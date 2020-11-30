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
@WebServlet(name = "LikePostServlet", urlPatterns = {"/LikePostServlet"})
public class LikePostServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(LikePostServlet.class);
    
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
            if (session != null) {
                TblUsersDTO currentUser = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (currentUser != null) {
                    String currentUserId = currentUser.getEmail();
                    
                    if (txtPostId != null) {
                        int postId = Integer.parseInt(txtPostId);

                        TblEmotionsDAO emotionsDAO = new TblEmotionsDAO();
                        TblPostsDAO postsDAO = new TblPostsDAO();
                        TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                        
                        String emotionBefore = emotionsDAO.getEmotionBefore(postId, currentUserId);
                        String postOwner = postsDAO.getUserPostId(postId);

                        if (emotionBefore != null) {
                            //If user has droped dislike emotion before
                            switch (emotionBefore) {
                                case "Dislike":
                                    //Change from Dislike to Like in tblEmotions
                                    emotionsDAO.updateLikePost(postId, currentUserId);
                                    
                                    //Update notification which created before.
                                    if(!postOwner.equals(currentUserId)) {
                                        notificationsDAO.updateEmotionNoti(postId, currentUserId, "Like");
                                    }   
                                    
                                    break;
                                
                                case "Like":
                                    //Delete emotion of current user for this post
                                    emotionsDAO.deleteEmotion(postId, currentUserId);
                                    
                                    //Remove notification
                                    int notiId = notificationsDAO.getEmotionNotificationId(postId, currentUserId);
                                    notificationsDAO.removeNotification(notiId);
                                    
                                    break;
                                    
                                case "Deleted":
                                    //Change isDeleted to false and emotion to Like
                                    emotionsDAO.updateLikePost(postId, currentUserId);
                                    
                                    break;
                            }
                        } else {
                            //First time this user drop like emotion
                            emotionsDAO.newLikePost(postId, currentUserId);
                            
                            //Create new notification for this emotion.
                            if(!postOwner.equals(currentUserId)) {
                                notificationsDAO.createEmotionNoti(postId, currentUserId, "Like");
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
