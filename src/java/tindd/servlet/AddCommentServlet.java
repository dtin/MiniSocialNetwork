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
import tindd.tblPosts.TblPostsDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "AddCommentServlet", urlPatterns = {"/AddCommentServlet"})
public class AddCommentServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(AddCommentServlet.class);
    
    private final String POST_DETAIL_CONTROLLER = "PostDetailServlet";
    private final String LOGIN_PAGE = "login.html";
    
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
        String txtCommentContent = request.getParameter("txtCommentContent");
        
        String url = LOGIN_PAGE;
        
        boolean isError = false;
        TblCommentsError error = new TblCommentsError();
        
        try {
            if(session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if(usersDTO != null && txtPostId != null && txtCommentContent != null) {
                    url = POST_DETAIL_CONTROLLER + "?txtPostId=" + txtPostId;
                    int postId = Integer.parseInt(txtPostId);
                    String currentUserId = usersDTO.getEmail();
                    TblPostsDAO postsDAO = new TblPostsDAO();
                    String postUserId = postsDAO.getUserPostId(postId);
                    
                    TblCommentsDAO commentsDAO = new TblCommentsDAO();
                    int commentId = commentsDAO.addCommentToPost(postId, currentUserId , txtCommentContent);
                    
                    if(commentId == -1) {
                        isError = true;
                        error.setAddCommentError("Can not add comment right now. Please try again later!");
                    } else {
                        if(!currentUserId.equals(postUserId)) {
                            TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                            notificationsDAO.createCommentNoti(postId, commentId, currentUserId);
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
