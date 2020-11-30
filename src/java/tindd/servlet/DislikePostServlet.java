/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblEmotions.TblEmotionsDAO;
import tindd.tblNotifications.TblNotificationsDAO;
import tindd.tblPosts.TblPostsDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "DislikePostServlet", urlPatterns = {"/DislikePostServlet"})
public class DislikePostServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DislikePostServlet.class);
    
    private final String POST_DETAIL_CONTROLLER = "PostDetailServlet";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        String txtPostId = request.getParameter("txtPostId");
        
        String url = POST_DETAIL_CONTROLLER + "?txtPostId=" + txtPostId;
        
        try {
            if(session != null) {
                TblUsersDTO currentUser = (TblUsersDTO) session.getAttribute(("ACCOUNT_INFO"));
                if(currentUser != null) {
                    String currentUserId = currentUser.getEmail();
                    
                    if(txtPostId != null) {
                        int postId = Integer.parseInt(txtPostId);
                        
                        TblEmotionsDAO emotionsDAO = new TblEmotionsDAO();
                        TblPostsDAO postsDAO = new TblPostsDAO();
                        TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                        
                        String emotionBefore = emotionsDAO.getEmotionBefore(postId, currentUserId);
                        String postOwner = postsDAO.getUserPostId(postId);
                        
                        if(emotionBefore != null) {
                            //If user has droped Like emotion before
                            switch (emotionBefore) {
                                case "Like":
                                    //Update value from Like to Dislike
                                    emotionsDAO.updateDislikePost(postId, currentUserId);
                                    
                                    //Update notification which created before only if current user is not post owner
                                    if(!currentUserId.equals(postOwner)) {
                                        notificationsDAO.updateEmotionNoti(postId, currentUserId, "Dislike");
                                    }   
                                    
                                    break;
                                    
                                case "Deleted":
                                    //Update deleted emotion to false and update emotion to dislike
                                    emotionsDAO.updateDislikePost(postId, currentUserId);
                                    
                                    if(!currentUserId.equals(postOwner)) {
                                        notificationsDAO.updateEmotionNoti(postId, currentUserId, "Dislike");
                                    }   
                                    
                                    break;
                                    
                                case "Dislike":
                                    //Delete emotion by set isDeleted to true
                                    emotionsDAO.deleteEmotion(postId, currentUserId);
                                    
                                    //Remove notification
                                    int notiId = notificationsDAO.getEmotionNotificationId(postId, currentUserId);
                                    notificationsDAO.removeNotification(notiId);
                                    
                                    break;
                            }
                        } else {
                            //This is the first time user drop Dislike
                            emotionsDAO.newDislikePost(postId, currentUserId);
                            
                            //Create new notification for this emotion action.
                            if(!currentUserId.equals(postOwner)) { 
                                notificationsDAO.createEmotionNoti(postId, currentUserId, "Dislike");
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
