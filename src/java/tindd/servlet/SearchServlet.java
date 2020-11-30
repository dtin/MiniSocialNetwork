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
import tindd.tblPosts.TblPostsDAO;
import tindd.tblPosts.TblPostsDTO;
import tindd.tblPosts.TblPostsSearchError;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {
    
    private final Logger LOGGER = Logger.getLogger(SearchServlet.class);

    private final String LOGIN_PAGE = "login.html";
    private final String SEARCH_PAGE = "searchPage.jsp";
    private final String HOME_CONTROLLER = "HomeServlet";
    private final String DATE_FORMAT_PARTERN = "EEE, dd MM yyyy, HH:mm:ss";
    private final int NUMBER_POSTS_PER_PAGE = 20;
    private final int MAX_NOTIFICATION = 30;
    private List<StandardHomePost> postList = null;

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
        int currentPage = 1;
        boolean isRedirect = true;
        boolean isError = false;

        HttpSession session = request.getSession(false);

        String txtSearch = request.getParameter("txtSearch");
        String txtPage = request.getParameter("page");
        TblPostsSearchError error = new TblPostsSearchError();
        List<TblPostsDTO> list = null;

        try {
            if (session != null) {
                TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                if (usersDTO != null) {
                    if (txtSearch == null || txtSearch.isEmpty()) {
                        url = HOME_CONTROLLER;
                    } else {
                        String currentUserId = usersDTO.getEmail();
                        request.setAttribute("FULL_NAME", usersDTO.getName());
                        request.setAttribute("USER_EMAIL", currentUserId);

                        if (txtPage != null) {
                            currentPage = Integer.parseInt(txtPage);
                        }

                        url = SEARCH_PAGE;
                        isRedirect = false;

                        TblPostsDAO postsDAO = new TblPostsDAO();
                        int searchCount = postsDAO.getSearchCount(txtSearch);

                        if (searchCount > 0) {
                            postsDAO.searchPost(txtSearch, currentPage, NUMBER_POSTS_PER_PAGE);

                            TblUsersDAO usersDAO = new TblUsersDAO();
                            StandardHomePost stdHomePost = null;
                            Date millis = null;
                            DateFormat simple = new SimpleDateFormat(DATE_FORMAT_PARTERN);

                            list = postsDAO.getPosts();

                            if (list != null) {
                                postList = new ArrayList<>();

                                for (TblPostsDTO post : list) {
                                    stdHomePost = new StandardHomePost();

                                    stdHomePost.setPostsDTO(post);

                                    //Set the full name of user post this
                                    String userFullName = usersDAO.getUserFullName(post.getUserPostId());
                                    stdHomePost.setUserFullName(userFullName);

                                    //Get human-readable time created post
                                    millis = new Date(post.getCreatedAt());
                                    String createdAtFull = simple.format(millis);

                                    stdHomePost.setCreatedAtFull(createdAtFull);

                                    postList.add(stdHomePost);
                                }
                            }

                            //Caculate how many pages are need from total posts
                            int maxPageCount;

                            if (searchCount % NUMBER_POSTS_PER_PAGE == 0) {
                                maxPageCount = searchCount / NUMBER_POSTS_PER_PAGE;
                            } else {
                                maxPageCount = searchCount / NUMBER_POSTS_PER_PAGE + 1;
                            }
                            
                            //Paging process
                            if(currentPage == maxPageCount && currentPage == 1) {
                                request.setAttribute("CURRENT_PAGE", currentPage);
                            } else if(currentPage < maxPageCount) {
                                if(currentPage == 1) {
                                    request.setAttribute("NEXT_PAGE", currentPage + 1);
                                    request.setAttribute("CURRENT_PAGE", currentPage);
                                } else {
                                    request.setAttribute("MIN_PAGE", 1);
                                    request.setAttribute("NEXT_PAGE", currentPage + 1);
                                    request.setAttribute("CURRENT_PAGE", currentPage);
                                    request.setAttribute("PREV_PAGE", currentPage - 1);
                                }
                                
                                request.setAttribute("MAX_PAGE", maxPageCount);
                            } else {
                                request.setAttribute("CURRENT_PAGE", currentPage);
                                request.setAttribute("PREV_PAGE", currentPage - 1);
                                request.setAttribute("MIN_PAGE", 1);
                            }
                        } else {
                            isError = true;
                            error.setSearchValueNotFound("Not found any post with value " + txtSearch + "!");
                        }
                        
                        //Get notification
                        TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                        boolean result = notificationsDAO.getAllNotifications(MAX_NOTIFICATION, currentUserId);
                        if(result) {
                            List<StandardNotification> notiList = notificationsDAO.getListNotification();
                            request.setAttribute("NOTI_LIST", notiList);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if (!isRedirect) {
                if (isError) {
                    request.setAttribute("ERROR", error);
                } else {
                    request.setAttribute("POSTS_LIST", postList);
                }

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
