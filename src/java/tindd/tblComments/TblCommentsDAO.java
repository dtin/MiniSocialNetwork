/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblComments;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblCommentsDAO implements Serializable {

    List<TblCommentsDTO> listComments = null;

    public List<TblCommentsDTO> getListComments() {
        return listComments;
    }

    public boolean getCommentsOfPost(int postId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT commentId, commentUser, commentContent, createdAt "
                        + "FROM tblComments "
                        + "WHERE postId=? AND isDeleted=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setBoolean(2, false);

                rs = stm.executeQuery();

                if (rs.next()) {
                    TblCommentsDTO commentsDTO = null;

                    do {
                        if (listComments == null) {
                            listComments = new ArrayList<>();
                        }

                        commentsDTO = new TblCommentsDTO();

                        //Get comment id
                        int commentId = rs.getInt("commentId");
                        commentsDTO.setCommentId(commentId);

                        //Get comment from which user
                        String commentUser = rs.getString("commentUser");
                        commentsDTO.setCommentUser(commentUser);

                        //Get comment content
                        String commentContent = rs.getString("commentContent");
                        commentsDTO.setCommentContent(commentContent);

                        //Get time comment create
                        long createdAt = rs.getLong("createdAt");
                        commentsDTO.setCreatedAt(createdAt);

                        listComments.add(commentsDTO);
                    } while (rs.next());
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

    /**
     * Insert new comment into database
     * @param postId
     * @param userComment
     * @param contentComment
     * @return Id comment of this insert
     * @throws SQLException
     * @throws NamingException 
     */
    public int addCommentToPost(int postId, String userComment, String contentComment)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        int result = -1;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblComments(postId, commentUser, commentContent, createdAt, isDeleted) "
                        + "VALUES(?, ?, ?, ?, ?)";

                stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                stm.setInt(1, postId);
                stm.setString(2, userComment);
                stm.setString(3, contentComment);
                stm.setLong(4, System.currentTimeMillis());
                stm.setBoolean(5, false);

                int rowCount = stm.executeUpdate();
                if(rowCount != 0) {
                    rs = stm.getGeneratedKeys();
                    if(rs.next()) {
                        result = rs.getInt(1);
                    }
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

    public boolean deleteComment(int commentId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblComments SET isDeleted=? WHERE commentId=?";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setInt(2, commentId);

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

    public int getCommentPostId(int commentId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int result = -1;
        
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT postId FROM tblComments WHERE commentId=? AND isDeleted=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, commentId);
                stm.setBoolean(2, false);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("postId");
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
    
    public String getCommentUserId(int commentId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String result = null;
        try {
            conn =DBHelper.makeConnection();
            if(conn != null) {
                String sql = "SELECT commentUser FROM tblComments WHERE commentId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, commentId);
                rs = stm.executeQuery();
                if(rs.next()) {
                    result = rs.getString("commentUser");
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
        
        return result;
    }
    
    public int getCommentCount(int postId) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        int result = -1;
        
        try {
            conn = DBHelper.makeConnection();
            if(conn != null) {
                String sql = "SELECT COUNT(commentId) AS countComments "
                        + "FROM tblComments "
                        + "WHERE postId=? AND isDeleted=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setBoolean(2, false);
                
                rs = stm.executeQuery();
                if(rs.next()) {
                    result = rs.getInt("countComments");
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
        
        return result;
    }
}
