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
import tindd.tblStatus.TblStatusDAO;
import tindd.tblUsers.TblUsersActivationError;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;
import tindd.utils.ActivationHelper;
import tindd.utils.MailSender;

/**
 *
 * @author Tin
 */
@WebServlet(name = "ResendActivationServlet", urlPatterns = {"/ResendActivationServlet"})
public class ResendActivationServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(ResendActivationServlet.class);
    
    private final String ACTIVATION_CONTROLLER = "ActivationServlet";
    
    
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
        String url = ACTIVATION_CONTROLLER;

        
        String statusName = null;
        boolean isError = false;
        
        TblUsersActivationError err = new TblUsersActivationError();
        
        try {
            if(session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if(usersDTO != null) {
                    int statusId = usersDTO.getStatusId();
                    if(statusId != -1) {
                        TblStatusDAO statusDAO = new TblStatusDAO();
                        statusName = statusDAO.getStatusName(statusId);
                        
                        if(statusName != null && statusName.equals("New")) {
                            
                            String currentUserEmail = usersDTO.getEmail();
                            
                            int activationCode = ActivationHelper.randomActivationNumber();
                            boolean isSent = MailSender.sendActivationCode(currentUserEmail, activationCode);
                            if(isSent) {
                                TblUsersDAO usersDAO = new TblUsersDAO();
                                boolean updateResult = usersDAO.updateActivationCode(activationCode, currentUserEmail);
                                if(!updateResult) {
                                    isError = true;
                                    err.setUpdateActivationCodeFail("Can not get new activation code right now!");
                                } else {
                                    request.setAttribute("SENT_SUCCESS", "Please check your inbox for new verification code.");
                                }
                            } else {
                                isError = true;
                                err.setSendEmailFail("Can not send email right now!");
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
            if(isError) {
                request.setAttribute("ERROR", err);
            }
            
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            
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
