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

/**
 *
 * @author Tin
 */
@WebServlet(name = "VerifyActivationServlet", urlPatterns = {"/VerifyActivationServlet"})
public class VerifyActivationServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(VerifyActivationServlet.class);
    private final String ACTIVATION_CONTROLLER = "ActivationServlet";
    private final String HOME_CONTROLLER = "HomeServlet";

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
        String txtCode = request.getParameter("txtCode");

        String url = ACTIVATION_CONTROLLER;
        boolean isRedirect = true;
        boolean isError = false;

        TblUsersActivationError err = new TblUsersActivationError();

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null && txtCode != null) {
                    String currentUserEmail = usersDTO.getEmail();

                    TblUsersDAO usersDAO = new TblUsersDAO();

                    int activationCode = usersDAO.getActivationCode(currentUserEmail);

                    //Compare activation code with value user input
                    if (txtCode.equals(activationCode + "")) {
                        TblStatusDAO statusDAO = new TblStatusDAO();

                        int activeId = statusDAO.getStatusId("Active");
                        boolean result = usersDAO.updateUserStatus(currentUserEmail, activeId);

                        if (result) {
                            usersDTO.setStatusId(activeId);
                            url = HOME_CONTROLLER;
                        }
                    } else {
                        isError = true;
                        err.setActivationCodeNotCorrect("Your code you enter is not correct. Please re-check again.");
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("NamingException: " + ex.getMessage());
        } finally {
            if (isError) {
                request.setAttribute("ERROR", err);
                isRedirect = false;
            }

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
