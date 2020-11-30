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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersRegisterError;
import tindd.utils.ActivationHelper;
import tindd.utils.Encryption;
import tindd.utils.MailSender;

/**
 *
 * @author Tin
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(RegisterServlet.class);
    
    private final String REGISTER_ERROR = "register.jsp";
    private final String LOGIN_PAGE = "login.html";
    private final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    
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
        
        String url = REGISTER_ERROR;
        boolean isError = false;
        TblUsersRegisterError error = new TblUsersRegisterError();
        try {
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter(("txtConfirm"));
            String name = request.getParameter("txtName");
            
            if(name.isEmpty()) {
                isError = true;
                error.setLengthName("Full name can not be empty");
                return;
            }
            
            if(!email.matches(EMAIL_PATTERN)) {
                isError = true;
                error.setFormatEmail("Email you entered was not the correct format!");
                return;
            }
            
            if(password.length() < 6 ) {
                isError = true;
                error.setLengthPassword("Password must greater than 6 digits!");
                return;
            }
            
            if(password.equals(confirm)) {
                String encryptedPass = Encryption.getEncryptedSHA(password);
                TblUsersDAO dao = new TblUsersDAO();
                int activationCode = ActivationHelper.randomActivationNumber();
                if(activationCode != -1) {
                    boolean result = dao.createAccount(email, encryptedPass, name, activationCode);

                    if(result) {
                        url = LOGIN_PAGE;
                        MailSender.sendActivationCode(email, activationCode);
                    }                
                }
            } else {
                isError = true;
                error.setConfirmPassword("Confirm password must be the same with password!");
            }
            
        } catch (SQLException ex) {
            if(ex.getMessage().contains("duplicate key")) {
               isError = true;
               error.setDuplicateEmail("The email you entered was registered before!");
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException: " + ex.getMessage());
        } finally {
            if(isError) {
                request.setAttribute("ERR", error);
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                url = LOGIN_PAGE;
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
