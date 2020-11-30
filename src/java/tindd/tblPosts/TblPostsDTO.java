/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblPosts;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblPostsDTO implements Serializable{
    private int postId;
    private String userPostId;
    private String postTitle;
    private String postContent;
    private String image;
    private boolean deleted;
    private long createdAt;

    public TblPostsDTO() {
    }

    public TblPostsDTO(int postId, String userPostId, String postTitle, String postContent, String image, boolean deleted, long createdAt) {
        this.postId = postId;
        this.userPostId = userPostId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.image = image;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUserPostId() {
        return userPostId;
    }

    public void setUserPostId(String userPostId) {
        this.userPostId = userPostId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }   
}
