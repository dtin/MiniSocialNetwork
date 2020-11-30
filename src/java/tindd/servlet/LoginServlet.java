/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;
import tindd.utils.Encryption;

/**
 *
 * @author Tin
 */
public class LoginServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    
    private final String LOGIN_PAGE = "login.html";
    private final String LOGIN_FAIL = "loginFail.html";
    private final String DISPATCH_CONTROLLER = "DispatchController";
    private final int EXPIRED_COOKIE_TIME = 60 * 60 * 24 * 7;

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

        String url = LOGIN_PAGE;

        try {
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String remember = request.getParameter("ckbRemember");

            TblUsersDTO dto = null;

            try {

                if (password != null) {
                    String encryptedPass = Encryption.getEncryptedSHA(password);
                    TblUsersDAO dao = new TblUsersDAO();

                    dto = dao.checkLogin(email, encryptedPass);
                } else {
                    return;
                }

                if (dto != null) {
                    if (remember != null) {
                        Cookie emailCookie = new Cookie("USER_EMAIL", dto.getEmail());
                        emailCookie.setMaxAge(EXPIRED_COOKIE_TIME);

                        Cookie passwordCookie = new Cookie("USER_PASS", dto.getPassword());
                        passwordCookie.setMaxAge(EXPIRED_COOKIE_TIME);

                        response.addCookie(emailCookie);
                        response.addCookie(passwordCookie);
                    }
                    
                    HttpSession session = request.getSession(true);
                    session.setAttribute("ACCOUNT_INFO", dto);
                    url = DISPATCH_CONTROLLER;

                } else {
                    url = LOGIN_FAIL;
                }
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("NoSuchAlgorithmException: " + ex.getMessage());
            } catch (NamingException ex) {
                LOGGER.error("NamingException: " + ex.getMessage());
            } catch (SQLException ex) {
                LOGGER.error("SQLException: " + ex.getMessage());
            }
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
