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
import tindd.tblNotifications.TblNotificationsDAO;
import tindd.tblUsers.TblUsersDTO;
import tindd.tblPosts.TblPostsDAO;
import tindd.tblPosts.TblPostsActionError;

/**
 *
 * @author Tin
 */
@WebServlet(name = "DeletePostServlet", urlPatterns = {"/DeletePostServlet"})
public class DeletePostServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DeleteCommentServlet.class);
    
    private final String HOME_CONTROLLER = "HomeServlet";
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

        boolean isError = false;
        TblPostsActionError error = new TblPostsActionError();

        HttpSession session = request.getSession(false);
        String txtPostId = request.getParameter("txtPostId");

        String url = HOME_CONTROLLER;

        try {
            if (txtPostId != null) {
                int postId = Integer.parseInt(txtPostId);
                
                if (session != null) {
                    TblUsersDTO userDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                    if (userDTO != null) {
                        String userId = userDTO.getEmail();
                        TblPostsDAO dao = new TblPostsDAO();
                        String userPostId = dao.getUserPostId(postId);
                        //Check if user have authorized to delete.
                        if (userId.equals(userPostId)) {
                            boolean result = dao.deletePost(postId);
                            if(result) {
                                TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                                notificationsDAO.removeAllNotificationFromPost(postId);
                            } else {
                                isError = true;
                                error.setDeleteFail("Delete post fail. Please try again later!");
                            }
                        } else {
                            isError = true;
                            error.setNotAuthorizedToDelete("You do not have authorized to delete this post!");
                        }
                    }
                }

            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (isError) {
                url = POST_DETAIL_CONTROLLER + "?txtPostId=" + txtPostId;
                request.setAttribute("ACTION_ERROR", error);
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
