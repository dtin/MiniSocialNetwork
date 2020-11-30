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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblStatus.TblStatusDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@MultipartConfig
@WebServlet(name = "DispatchController", urlPatterns = {"/DispatchController"})
public class DispatchController extends HttpServlet {
    
    private final Logger LOGGER = Logger.getLogger(DispatchController.class);
    
    private final String STARTUP_CONTROLLER = "StartupServlet";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String REGISTER_CONTROLLER = "RegisterServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String HOME_CONTROLLER = "HomeServlet";
    private final String NEW_POST_CONTROLLER = "NewPostServlet";
    private final String DELETE_POST_CONTROLLER = "DeletePostServlet";
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String POST_DETAIL_CONTROLLER = "PostDetailServlet";
    private final String LIKE_POST_CONTROLLER = "LikePostServlet";
    private final String DISLIKE_POST_CONTROLLER = "DislikePostServlet";
    private final String ADD_COMMENT_CONTROLLER = "AddCommentServlet";
    private final String DELETE_COMMENT_CONTROLLER = "DeleteCommentServlet";
    private final String ACTIVATION_CONTROLLER = "ActivationServlet";
    private final String RESEND_ACTIVATION_CONTROLLER = "ResendActivationServlet";
    private final String VERIFY_ACTIVATION_CONTROLLER = "VerifyActivationServlet";

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
        String url = STARTUP_CONTROLLER;
        boolean isRedirect = false;

        String btnAction = request.getParameter("btnAction");
        
        
        try {
            TblStatusDAO statusDAO = new TblStatusDAO();
            int newAccountId = statusDAO.getStatusId("New");
            int activeAccountId = statusDAO.getStatusId("Active");
            
            if (btnAction != null) {
                if (btnAction.equals("Login")) {
                    url = LOGIN_CONTROLLER;
                } else if (btnAction.equals("Register")) {
                    url = REGISTER_CONTROLLER;
                } else if (session != null) {
                    TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO"); 
                    if (usersDTO != null) {
                        int currentStatus = usersDTO.getStatusId();
                        
                        if(btnAction.equals("Logout")) {
                            url = LOGOUT_CONTROLLER;
                            isRedirect = true;
                        } else if(currentStatus - newAccountId == 0) {
                            switch(btnAction) {
                                case "ResendCode":
                                    url = RESEND_ACTIVATION_CONTROLLER;
                                    isRedirect = true;
                                    break;
                                case "Verify":
                                    url = VERIFY_ACTIVATION_CONTROLLER;
                                    break;
                                default:
                                    url = ACTIVATION_CONTROLLER;
                                    break;
                            }
                        } else if(currentStatus - activeAccountId == 0) {
                            switch (btnAction) {
                                case "AddPost":
                                    url = NEW_POST_CONTROLLER;
                                    break;
                                case "DeletePost":
                                    url = DELETE_POST_CONTROLLER;
                                    break;
                                case "Search":
                                    url = SEARCH_CONTROLLER;
                                    break;
                                case "PostDetail":
                                    url = POST_DETAIL_CONTROLLER;
                                    break;
                                case "PostLike":
                                    url = LIKE_POST_CONTROLLER;
                                    break;
                                case "PostDislike":
                                    url = DISLIKE_POST_CONTROLLER;
                                    break;
                                case "DeleteComment":
                                    url = DELETE_COMMENT_CONTROLLER;
                                    break;
                                case "AddComment":
                                    url = ADD_COMMENT_CONTROLLER;
                                    break;
                                default:
                                    url = HOME_CONTROLLER;
                                    isRedirect = true;
                                    break;
                            }
                        
                        }
                    }
                }
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } finally {
            if (isRedirect) {
                response.sendRedirect(url);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
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
