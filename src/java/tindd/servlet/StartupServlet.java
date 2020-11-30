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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.tblStatus.TblStatusDAO;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(StartupServlet.class);

    private final String HOME_CONTROLLER = "HomeServlet";
    private final String ACTIVATION_CONTROLLER = "ActivationServlet";
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

        String url = LOGIN_PAGE;

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    int currentStatusId = usersDTO.getStatusId();

                    TblStatusDAO statusDAO = new TblStatusDAO();
                    int activeAccountStatus = statusDAO.getStatusId("Active");
                    int newAccountStatus = statusDAO.getStatusId("New");

                    if (currentStatusId - newAccountStatus == 0) {
                        url = ACTIVATION_CONTROLLER;
                    } else if (currentStatusId - activeAccountStatus == 0) {
                        url = HOME_CONTROLLER;
                    }
                }
            } else {
                Cookie[] cookies = request.getCookies();
                String email = null;
                String password = null;

                if (cookies != null) {
                    for (Cookie cooky : cookies) {
                        if (cooky.getName().equals("USER_EMAIL")) {
                            email = cooky.getValue();
                        } else if (cooky.getName().equals("USER_PASS")) {
                            password = cooky.getValue();
                        }
                        if (email != null && password != null) {
                            break;
                        }
                    }
                }

                if (email != null && password != null) {
                    TblUsersDAO dao = new TblUsersDAO();
                    TblUsersDTO dto = dao.checkLogin(email, password);

                    if (dto != null) {
                        session = request.getSession();
                        session.setAttribute("ACCOUNT_INFO", dto);
                        url = HOME_CONTROLLER;
                    }
                }
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } finally {
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
