/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblNotifications;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import tindd.notification.StandardNotification;
import tindd.tblPosts.TblPostsDAO;
import tindd.tblUsers.TblUsersDAO;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblNotificationsDAO implements Serializable {

    private List<StandardNotification> listNotification;

    public TblNotificationsDAO() {
    }

    public List<StandardNotification> getListNotification() {
        return listNotification;
    }

    public boolean getAllNotifications(int numberOfNotification, String notiUserId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT TOP " + numberOfNotification + " notificationId, postId, commentId, contentNoti, ISNULL(updatedAt, createdAt) AS notiDate "
                        + "FROM tblNotifications "
                        + "WHERE notiUserId=? AND isDeleted=? "
                        + "ORDER BY notiDate DESC";

                stm = conn.prepareStatement(sql);
                stm.setString(1, notiUserId);
                stm.setBoolean(2, false);

                rs = stm.executeQuery();

                if (rs.next()) {
                    listNotification = new ArrayList<>();
                    TblNotificationsDTO notificationsDTO = null;
                    StandardNotification stdNoti = null;

                    Date date = null;
                    DateFormat simple = new SimpleDateFormat("EEE, dd MMM, yyyy HH:mm:ss");

                    do {
                        notificationsDTO = new TblNotificationsDTO();
                        stdNoti = new StandardNotification();

                        //Get notificationId
                        int notificationId = rs.getInt("notificationId");
                        notificationsDTO.setNotificationId(notificationId);

                        //Get postId
                        int postId = rs.getInt("postId");
                        notificationsDTO.setPostId(postId);

                        //Get commentId (if has)
                        int commentId = rs.getInt("commentId");
                        notificationsDTO.setCommentId(commentId);
                        
                        //Get content notification
                        String contentNoti = rs.getString("contentNoti");
                        notificationsDTO.setContentNoti(contentNoti);

                        //Get date of notification and set to standard notification
                        long notiDate = rs.getLong("notiDate");
                        date = new Date(notiDate);
                        stdNoti.setDateAtFull(simple.format(date));

                        //Set notificationDTO to standard notification
                        stdNoti.setNotificationsDTO(notificationsDTO);

                        listNotification.add(stdNoti);
                    } while (rs.next());

                    result = true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public int getEmotionNotificationId(int postId, String userDropEmotion)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int notificationId = -1;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT notificationId "
                        + "FROM tblNotifications "
                        + "WHERE postId=? AND fromUserId=? AND typeOfNoti=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setString(2, userDropEmotion);
                stm.setString(3, "Emotion");

                rs = stm.executeQuery();

                if (rs.next()) {
                    notificationId = rs.getInt("notificationId");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return notificationId;
    }

    public boolean createEmotionNoti(int postId, String userDropEmotion, String typeOfEmotion)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                TblPostsDAO postsDAO = new TblPostsDAO();
                TblUsersDAO userDAO = new TblUsersDAO();

                String sql = "INSERT INTO tblNotifications(postId, notiUserId, fromUserId, typeOfNoti, contentNoti, createdAt, isDeleted) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);

                //Set post identity
                stm.setInt(1, postId);

                //Set who owner the post
                String postOwner = postsDAO.getUserPostId(postId);
                stm.setString(2, postOwner);

                //Set who drop the emotion
                stm.setString(3, userDropEmotion);

                //Set type of notification
                stm.setString(4, "Emotion");

                //Set content of emotion
                typeOfEmotion = typeOfEmotion.toLowerCase();
                String nameUserDropEmotion = userDAO.getUserFullName(userDropEmotion);
                String notiContent = nameUserDropEmotion + " " + typeOfEmotion + "d your post";
                stm.setString(5, notiContent);

                //Set current time
                stm.setLong(6, System.currentTimeMillis());

                //Set delete default value
                stm.setBoolean(7, false);

                int rowCount = stm.executeUpdate();

                if (rowCount != 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean updateEmotionNoti(int postId, String userDropEmotion, String typeOfEmotion)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                TblUsersDAO userDAO = new TblUsersDAO();

                String sql = "UPDATE tblNotifications "
                        + "SET contentNoti=?, updatedAt=?, isDeleted=? "
                        + "WHERE postId=? AND fromUserId=? AND typeOfNoti=?";

                stm = conn.prepareStatement(sql);

                //Set content of notification
                typeOfEmotion = typeOfEmotion.toLowerCase();
                String nameUserDropEmotion = userDAO.getUserFullName(userDropEmotion);
                String contentNoti = nameUserDropEmotion + " " + typeOfEmotion + "d your post";
                stm.setString(1, contentNoti);

                //Set last update time
                stm.setLong(2, System.currentTimeMillis());

                //Set delete status to false
                stm.setBoolean(3, false);

                //Set post identity
                stm.setInt(4, postId);

                //Set who drop emotion
                stm.setString(5, userDropEmotion);

                //Set type of notification
                stm.setString(6, "Emotion");

                int rowCount = stm.executeUpdate();
                if (rowCount != 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean createCommentNoti(int postId, int commentId, String fromUserId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblNotifications(postId, commentId, notiUserId, fromUserId, typeOfNoti, contentNoti, createdAt, isDeleted) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);

                //Set post id
                stm.setInt(1, postId);

                //Set comment id
                stm.setInt(2, commentId);

                //Get user id of who owner this post
                TblPostsDAO postsDAO = new TblPostsDAO();
                String postOwner = postsDAO.getUserPostId(postId);

                //Set user id who's post owner
                stm.setString(3, postOwner);

                //Set this notification send from who
                stm.setString(4, fromUserId);

                //Set type of this notification
                stm.setString(5, "Comment");

                //Set notification content for this notification
                TblUsersDAO usersDAO = new TblUsersDAO();
                String commentUserFullName = usersDAO.getUserFullName(fromUserId);
                String contentNoti = commentUserFullName + " commented on your post";
                stm.setString(6, contentNoti);

                //Set time create this notification
                stm.setLong(7, System.currentTimeMillis());

                //Set default delete value
                stm.setBoolean(8, false);

                int rowCount = stm.executeUpdate();
                if (rowCount != 0) {
                    result = true;
                }

            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public int getCommentNotificationId(int commentId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int result = -1;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT notificationId FROM tblNotifications WHERE commentId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, commentId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("notificationId");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean removeNotification(int notificationId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblNotifications "
                        + "SET isDeleted=?, updatedAt=? "
                        + "WHERE notificationId=?";

                stm = conn.prepareStatement(sql);

                //Set notificationId
                stm.setBoolean(1, true);
                stm.setLong(2, System.currentTimeMillis());
                stm.setInt(3, notificationId);

                int rowCount = stm.executeUpdate();

                if (rowCount != 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean removeAllNotificationFromPost(int postId) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblNotifications "
                        + "SET isDeleted=?, updatedAt=? "
                        + "WHERE postId=?";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setLong(2, System.currentTimeMillis());
                stm.setInt(3, postId);
                int rowCount = stm.executeUpdate();
                if (rowCount != 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
}
