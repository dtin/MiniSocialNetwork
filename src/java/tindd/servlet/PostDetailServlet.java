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
import tindd.comment.StandardComment;
import tindd.notification.StandardNotification;
import tindd.post.StandardPostDetail;
import tindd.tblComments.TblCommentsDAO;
import tindd.tblComments.TblCommentsDTO;
import tindd.tblEmotions.TblEmotionsDAO;
import tindd.tblNotifications.TblNotificationsDAO;
import tindd.tblPosts.TblPostsDAO;
import tindd.tblPosts.TblPostsDTO;
import tindd.tblPosts.TblPostsDetailError;
import tindd.tblUsers.TblUsersDAO;
import tindd.tblUsers.TblUsersDTO;

/**
 *
 * @author Tin
 */
@WebServlet(name = "PostDetailServlet", urlPatterns = {"/PostDetailServlet"})
public class PostDetailServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(PostDetailServlet.class);
    
    private final String LOGIN_PAGE = "login.html";
    private final String POST_DETAIL_PAGE = "postDetail.jsp";
    private final String DATE_FORMAT_PARTERN = "EEE, dd MM, yyyy HH:mm:ss";
    private final int MAX_NOTIFICATION = 30;
    private List<StandardComment> stdCommentsList = null;

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

        String txtPostId = request.getParameter("txtPostId");
        HttpSession session = request.getSession(false);

        String url = LOGIN_PAGE;
        
        boolean isError = false;
        TblPostsDetailError error = new TblPostsDetailError();

        try {
            if (txtPostId != null) {
                int postId = Integer.parseInt(txtPostId);
                if (session != null) {
                    TblUsersDTO usersDTO = (TblUsersDTO) session.getAttribute("ACCOUNT_INFO");
                    if (usersDTO != null) {
                        url = POST_DETAIL_PAGE;
                        
                        //Set Full name and Email for welcome and update delete post function
                        String currentUserId = usersDTO.getEmail();
                        
                        request.setAttribute("FULL_NAME", usersDTO.getName());
                        request.setAttribute("USER_EMAIL", currentUserId);
                        
                        TblPostsDAO postsDAO = new TblPostsDAO();
                        TblPostsDTO singlePost = postsDAO.getSpecificPost(postId);
                        if(singlePost != null) {
                            List<TblCommentsDTO> listComments = null;
                            Date millis = null;
                            DateFormat simple = new SimpleDateFormat(DATE_FORMAT_PARTERN);
                            
                            StandardPostDetail stdPostDetail = new StandardPostDetail();
                            stdPostDetail.setPostsDTO(singlePost);
                            
                            //Convert time from millis to human-readable
                            Date date = new Date(singlePost.getCreatedAt());
                            stdPostDetail.setCreatedAtFull(simple.format(date));
                            
                            //Convert email to full name
                            TblUsersDAO usersDAO = new TblUsersDAO();
                            String userPostFullName = usersDAO.getUserFullName(singlePost.getUserPostId());
                            stdPostDetail.setUserFullName(userPostFullName);
                            
                            //Get the emotion from current user for this post.
                            TblEmotionsDAO emotionsDAO = new TblEmotionsDAO();
                            String userEmotion = emotionsDAO.getEmotionBefore(postId, currentUserId);
                            stdPostDetail.setEmotion(userEmotion);
                            
                            //Get number of like for this post
                            int countLike = emotionsDAO.getLikeCount(postId);
                            stdPostDetail.setCountLike(countLike);
                            
                            //Get number of dislike for this post
                            int countDislike = emotionsDAO.getDislikeCount(postId);
                            stdPostDetail.setCountDislike(countDislike);
                            
                            //Get number of comment for this post
                            TblCommentsDAO commentsDAO = new TblCommentsDAO();
                            int countComment = commentsDAO.getCommentCount(postId);
                            stdPostDetail.setCountComment(countComment);
                            
                            //Set this post to request scope
                            request.setAttribute("SINGLE_POST", stdPostDetail);
                            
                            //If found post successful, load comments
                            if(countComment > 0) {
                                commentsDAO.getCommentsOfPost(postId);
                                listComments = commentsDAO.getListComments();
                            }
                            
                            //If list of comments is not null
                            if(listComments != null) {
                                StandardComment stdComment = null;
                                stdCommentsList = new ArrayList<>();
                                
                                String userCommentId = null;
                                String userFullName = null;
                                
                                for (TblCommentsDTO comment : listComments) {
                                    stdComment = new StandardComment();

                                    stdComment.setCommentsDTO(comment);
                                    
                                    //Get user post comment's name
                                    userCommentId = comment.getCommentUser();
                                    userFullName = usersDAO.getUserFullName(userCommentId);
                                    stdComment.setUserFullName(userFullName);
                                    
                                    //Get time as millis format
                                    millis = new Date(comment.getCreatedAt());
                                    
                                    //Change to human-readable format
                                    stdComment.setCreatedAtFull(simple.format(millis));
                                    
                                    stdCommentsList.add(stdComment);
                                }
                                
                                request.setAttribute("STD_COMMENTS_LIST", stdCommentsList);
                            }
                            
                        } else {
                            //Nem loi ra: Loi khong tim thay post do da xoa hoac khong tim thay tren db
                            isError = true;
                            error.setNotFoundPost("This post is not exist or has been deleted");
                        }
                        
                        //Get notification for current user
                        TblNotificationsDAO notificationsDAO = new TblNotificationsDAO();
                        notificationsDAO.getAllNotifications(MAX_NOTIFICATION, currentUserId);
                        List<StandardNotification> notiList = notificationsDAO.getListNotification();
                        request.setAttribute("NOTI_LIST", notiList);                        
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            if(isError) {
                request.setAttribute("POST_ERROR", error);
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
