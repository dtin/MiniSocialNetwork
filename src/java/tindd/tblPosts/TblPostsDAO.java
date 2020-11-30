/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblPosts;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblPostsDAO implements Serializable {

    private List<TblPostsDTO> posts = null;

    public List<TblPostsDTO> getPosts() {
        return posts;
    }

    public String getUserPostId(int postId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String result = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT userPostId FROM tblPosts WHERE postId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getString("userPostId");
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

    public boolean createPostWithoutImage(String postTitle, String postContent, String userPostId)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblPosts(userPostId, postTitle, postContent, isDeleted, createdAt) "
                        + "VALUES (?, ?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userPostId);
                stm.setString(2, postTitle);
                stm.setString(3, postContent);
                stm.setBoolean(4, false);
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

    public boolean createPostWithImage(String postTitle, String postContent, String userPostId, String fileName)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblPosts(userPostId, postTitle, postContent, image, isDeleted, createdAt) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userPostId);
                stm.setString(2, postTitle);
                stm.setString(3, postContent);
                stm.setString(4, fileName);
                stm.setBoolean(5, false);
                stm.setLong(6, System.currentTimeMillis());

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

    public boolean deletePost(int postId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblPosts "
                        + "SET isDeleted=? "
                        + "WHERE postId=?";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setInt(2, postId);
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

    public boolean updatePost(int postId, String postContent)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblPosts "
                        + "SET postContent=? "
                        + "WHERE postId=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, postContent);
                stm.setInt(2, postId);
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

    public boolean getNewestPosts(int numberOfPosts)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblPostsDTO dto = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT TOP " + numberOfPosts + " postId, userPostId, postTitle, postContent, image, createdAt "
                        + "FROM tblPosts "
                        + "WHERE isDeleted=? "
                        + "ORDER BY createdAt DESC";

                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, false);

                rs = stm.executeQuery();

                if (rs.next()) {
                    if (getPosts() == null) {
                        posts = new ArrayList<>();
                    }

                    do {
                        //Get other results of ResultSet (if have)
                        dto = new TblPostsDTO();

                        dto.setPostId(rs.getInt("postId"));
                        dto.setUserPostId(rs.getString("userPostId"));
                        dto.setDeleted(false);
                        dto.setPostTitle(rs.getString("postTitle"));
                        dto.setPostContent(rs.getString("postContent"));
                        dto.setImage(rs.getString("image"));
                        dto.setCreatedAt(rs.getLong("createdAt"));

                        getPosts().add(dto);
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

    public TblPostsDTO getSpecificPost(int postId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblPostsDTO postsDTO = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT userPostId, postTitle, postContent, image, createdAt "
                        + "FROM tblPosts "
                        + "WHERE postId=? AND isDeleted=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, postId);
                stm.setBoolean(2, false);

                rs = stm.executeQuery();

                if (rs.next()) {
                    postsDTO = new TblPostsDTO();

                    postsDTO.setPostId(postId);

                    //Get id of who post (email)
                    String userPostId = rs.getString("userPostId");
                    postsDTO.setUserPostId(userPostId);

                    //Get post title
                    String postTitle = rs.getString("postTitle");
                    postsDTO.setPostTitle(postTitle);

                    //Get post content
                    String postContent = rs.getString("postContent");
                    postsDTO.setPostContent(postContent);

                    //Get image (if has)
                    String imagePath = rs.getString("image");
                    postsDTO.setImage(imagePath);
                    
                    //Get time create post
                    long createdAt = rs.getLong("createdAt");
                    postsDTO.setCreatedAt(createdAt);
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

        return postsDTO;
    }

    public boolean searchPost(String searchValue, int page, int postPerPage)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT postId, userPostId, postTitle, postContent, image, createdAt "
                        + "FROM tblPosts "
                        + "WHERE isDeleted=? AND postContent LIKE ? "
                        + "ORDER BY createdAt DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setString(2, "%" + searchValue + "%");
                stm.setInt(3, (page - 1) * postPerPage);
                stm.setInt(4, postPerPage);

                rs = stm.executeQuery();
                if (rs.next()) {
                    posts = new ArrayList<>();
                    TblPostsDTO postsDTO = null;

                    do {
                        postsDTO = new TblPostsDTO();

                        //Get postId
                        int postId = rs.getInt("postId");
                        postsDTO.setPostId(postId);

                        //Get user who post this
                        String userPostId = rs.getString("userPostId");
                        postsDTO.setUserPostId(userPostId);

                        //Get post title
                        String postTitle = rs.getString("postTitle");
                        postsDTO.setPostTitle(postTitle);

                        //Get post content
                        String postContent = rs.getString("postContent");
                        postsDTO.setPostContent(postContent);
                        
                        //Get image (if has)
                        String imagePath = rs.getString("image");
                        postsDTO.setImage(imagePath);

                        //Get time this post created
                        long createdAt = rs.getLong("createdAt");
                        postsDTO.setCreatedAt(createdAt);

                        posts.add(postsDTO);
                    } while (rs.next());
                }

                result = true;
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

    public int getSearchCount(String searchValue)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int result = -1;
        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(*) AS Count "
                        + "FROM tblPosts "
                        + "WHERE isDeleted=? AND postContent LIKE ? ";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setString(2, "%" + searchValue + "%");
                rs = stm.executeQuery();

                if (rs.next()) {
                    result = rs.getInt("Count");
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
}
