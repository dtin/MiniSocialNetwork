/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tindd.notification.StandardNotification;
import tindd.post.StandardHomePost;
import tindd.tblNotifications.TblNotificationsDAO;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;
import tindd.tblPosts.TblPostsDAO;
import tindd.tblPosts.TblPostsDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/HomeServlet"})
public class HomeServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(HomeServlet.class);
    
    private final String HOME_PAGE = "homePage.jsp";
    private final String LOGIN_PAGE = "login.html";
    private final int MAX_POSTS = 20;
    private final int MAX_NOTIFICATION = 30;
    private List<StandardHomePost> postsList = null;
    private final String DATE_FORMAT_PARTERN = "EEE, dd MM, yyyy HH:mm:ss";

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
                TblUsersDTO userDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (userDTO != null) {
                    url = HOME_PAGE;
                    
                    TblPostsDAO postsDAO = new TblPostsDAO();
                    TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                    
                    request.setAttribute("FULL_NAME", userDTO.getName());
                    
                    //Get userId of this current user sign in
                    String currentUserId = userDTO.getEmail();
                    request.setAttribute("USER_EMAIL", currentUserId);
                    
                    //Get notification for current user
                    notificationsDAO.getAllNotifications(MAX_NOTIFICATION, currentUserId);
                    List<StandardNotification> notiList = notificationsDAO.getListNotification();
                    request.setAttribute("NOTI_LIST", notiList);
                    
                    //Try to get 20 newest post
                    boolean result = postsDAO.getNewestPosts(MAX_POSTS);
                    
                    if(result) {
                        //Get raw posts (Without other informations)
                        List<TblPostsDTO> rawPosts = postsDAO.getPosts();
                        
                        postsList = new ArrayList<>();
                        TblUsersDAO usersDAO = new TblUsersDAO();
                        
                        DateFormat simple = new SimpleDateFormat(DATE_FORMAT_PARTERN);
                        Date millis = null;
                        
                        for (TblPostsDTO post : rawPosts) {
                            String userPostId = post.getUserPostId();
                            
                            StandardHomePost sdHomePost = new StandardHomePost();
                            
                            //Set postDTO for StandardHomePost
                            sdHomePost.setPostsDTO(post);
                            
                            //Get full name of the one who post
                            String userFullName = usersDAO.getUserFullName(userPostId);
                            sdHomePost.setUserFullName(userFullName);
                            
                            //Get full time in format
                            millis = new Date(post.getCreatedAt());
                            sdHomePost.setCreatedAtFull(simple.format(millis));
                            
                            postsList.add(sdHomePost);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if(postsList != null) {
                request.setAttribute("POSTS_LIST", postsList);
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
