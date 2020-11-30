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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblComments.TblCommentsDAO;
import tindd.tblComments.TblCommentsError;
import tindd.tblNotifications.TblNotificationsDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "DeleteCommentServlet", urlPatterns = {"/DeleteCommentServlet"})
public class DeleteCommentServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DeleteCommentServlet.class);
    
    private final String LOGIN_PAGE = "login.html";
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
        
        String txtCommentId = request.getParameter("txtCommentId");
        String url = LOGIN_PAGE;
        boolean isError = false;
        TblCommentsError error = new TblCommentsError();
        
        try {
            if(session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if(usersDTO != null && txtCommentId != null) {
                    
                    TblCommentsDAO commentsDAO = new TblCommentsDAO();
                    
                    int commentId = Integer.parseInt(txtCommentId);
                    int postId = commentsDAO.getCommentPostId(commentId);
                    
                    if(postId != -1) {
                        url = POST_DETAIL_CONTROLLER + "?txtPostId=" + postId; 

                        String currentUserId = usersDTO.getEmail();
                        String commentUserId = commentsDAO.getCommentUserId(commentId);
                        if(currentUserId.equals(commentUserId)) {
                            //Case this user is authorize to delete this comment
                            commentsDAO.deleteComment(commentId);
                            
                            //Also delete notification
                            TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                            int notiId = notificationsDAO.getCommentNotificationId(commentId);
                            if(notiId > 0) {
                                notificationsDAO.removeNotification(notiId);
                            }
                        } else {
                            //Case this user is not authorized
                            isError = true;
                            error.setNotAuthorizedToDelete("You do not have authorized to delete this comment!");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if(isError) {
                request.setAttribute("COMMENT_ERROR", error);
                request.setAttribute("COMMENT_ID", txtCommentId);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
            
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
