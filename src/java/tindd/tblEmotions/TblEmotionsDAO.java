/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblEmotions;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblEmotionsDAO implements Serializable {
    public String getEmotionBefore(int postId, String emotionUserId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String result = null;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT emotion, isDeleted "
                        + "FROM tblEmotions "
                        + "WHERE postId=? AND emotionUserId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setString(2, emotionUserId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean isDeleted = rs.getBoolean("isDeleted");
                    
                    if(!isDeleted) {
                        result = rs.getString("emotion");
                    } else {
                        result = "Deleted";
                    }
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

    public boolean deleteEmotion(int postId, String emotionUserId) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblEmotions "
                        + "SET isDeleted=? "
                        + "WHERE postId=? AND emotionUserId=?";

                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setInt(2, postId);
                stm.setString(3, emotionUserId);

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

    public boolean newLikePost(int postId, String emotionUserId)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblEmotions(postId, emotionUserId, isDeleted, emotion, createdAt) "
                        + "VALUES (?, ?, ?, ?, ?)";

                stm = conn.prepareStatement(sql);

                stm.setInt(1, postId);
                stm.setString(2, emotionUserId);
                stm.setBoolean(3, false);
                stm.setString(4, "Like");
                stm.setLong(5, System.currentTimeMillis());

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

    public boolean newDislikePost(int postId, String emotionUserId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblEmotions(postId, emotionUserId, isDeleted, emotion, createdAt) "
                        + "VALUES(?, ?, ?, ?, ?)";

                stm = conn.prepareStatement(sql);

                stm.setInt(1, postId);
                stm.setString(2, emotionUserId);
                stm.setBoolean(3, false);
                stm.setString(4, "Dislike");
                stm.setLong(5, System.currentTimeMillis());

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

    public boolean updateLikePost(int postId, String emotionUserId)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblEmotions "
                        + "SET emotion=?, updatedAt=?, isDeleted=? "
                        + "WHERE postId=? AND emotionUserId=?";

                stm = conn.prepareStatement(sql);

                stm.setString(1, "Like");
                stm.setLong(2, System.currentTimeMillis());
                stm.setBoolean(3, false);
                stm.setInt(4, postId);
                stm.setString(5, emotionUserId);

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

    public boolean updateDislikePost(int postId, String emotionUserId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblEmotions "
                        + "SET emotion=?, updatedAt=?, isDeleted=? "
                        + "WHERE postId=? AND emotionUserId=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "Dislike");
                stm.setLong(2, System.currentTimeMillis());
                stm.setBoolean(3, false);
                stm.setInt(4, postId);
                stm.setString(5, emotionUserId);

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
    
    public int getLikeCount(int postId) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        int count = -1;
        
        try {
            conn = DBHelper.makeConnection();
            if(conn != null) {
                String sql = "SELECT COUNT(emotion) AS countLike "
                        + "FROM tblEmotions "
                        + "WHERE postId=? AND emotion=? AND isDeleted=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setString(2, "Like");
                stm.setBoolean(3, false);
                rs = stm.executeQuery();
                
                if(rs.next()) {
                    count = rs.getInt("countLike");
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            
            if(stm != null) {
                stm.close();
            }
            
            if(conn != null){
                conn.close();
            }
        }
        
        return count;
    }
    
    public int getDislikeCount(int postId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        int count = -1;
        
        try {
            conn = DBHelper.makeConnection();
            if(conn != null) {
                String sql = "SELECT COUNT(emotion) AS countDislike "
                        + "FROM tblEmotions "
                        + "WHERE postId=? AND emotion=? AND isDeleted=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setString(2, "Dislike");
                stm.setBoolean(3, false);
                
                rs = stm.executeQuery();
                if(rs.next()) {
                    count = rs.getInt("countDislike");
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            
            if(stm != null) {
                stm.close();
            }
            
            if(conn != null) {
                conn.close();
            }
        }
        
        return count;
    }
}
