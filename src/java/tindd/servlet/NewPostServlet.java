/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;
import tindd.tblUsers.TblUsersDTO;
import tindd.tblPosts.TblPostsDAO;
import tindd.tblPosts.TblPostsActionError;

/**
 *
 * @author Tin
 */
@WebServlet(name = "NewPostServlet", urlPatterns = {"/NewPostServlet"})
@MultipartConfig
public class NewPostServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(NewPostServlet.class);
    
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
        String postTitle = request.getParameter("txtPostTitle");
        String postContent = request.getParameter("txtPostContent");

        ServletContext context = request.getServletContext();

        Part image = request.getPart("imageUpload");
        
        String url = HOME_CONTROLLER;
        boolean isError = false;
        boolean result = false;
        TblPostsActionError error = new TblPostsActionError();
        
        InputStream imageStream = null;
        OutputStream outputImage = null;

        try {
            if (postContent != null) {
                if (postContent.isEmpty()) {
                    isError = true;
                    error.setEmptyPost("Post body can not be empty");
                } else if (postTitle.isEmpty()) {
                    isError = true;
                    error.setEmptyTitle("Post title can not be empty");
                } else {
                    if (session != null) {
                        TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                        if (usersDTO != null) {
                            TblPostsDAO dao = new TblPostsDAO();
                            String currentUserId = usersDTO.getEmail();
                            if (image.getSize() > 0) {
                                //Get upload filename
                                String fullPath = image.getSubmittedFileName();
                                int indexLastDivider = fullPath.lastIndexOf("\\");
                                String fileName = System.currentTimeMillis() + "_" + fullPath.substring(indexLastDivider + 1);

                                //Get input stream of upload file
                                imageStream = image.getInputStream();
                                
                                //Set where to store file
                                String localPath = context.getRealPath("/");
                                File file = new File(localPath + "/assets/images/" + fileName);
                                
                                //Read until end of file (== -1)
                                int read = -1;
                                if (file.createNewFile()) {
                                    outputImage = new FileOutputStream(file);
                                    while ((read = imageStream.read()) != -1) {
                                        outputImage.write(read);
                                    }
                                }

                                result = dao.createPostWithImage(postTitle, postContent, currentUserId, fileName);
                            } else {
                                result = dao.createPostWithoutImage(postTitle, postContent, currentUserId);
                            }

                            if (!result) {
                                isError = true;
                                error.setPostFail("Create new post failed. Please try again later!");
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
            if(outputImage != null) {
                outputImage.close();
            }
            
            if(imageStream != null) {
                imageStream.close();
            }
            
            if (isError) {
                request.setAttribute("ERROR", error);
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
