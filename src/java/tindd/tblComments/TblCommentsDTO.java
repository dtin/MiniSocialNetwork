/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblComments;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblCommentsDTO implements Serializable{
    private int commentId;
    private int postId;
    private String commentUser;
    private String commentContent;
    private long createdAt;
    private boolean isDeleted;

    public TblCommentsDTO() {
    }

    public TblCommentsDTO(int commentId, int postId, String commentUser, String commentContent, long createdAt, boolean isDeleted) {
        this.commentId = commentId;
        this.postId = postId;
        this.commentUser = commentUser;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
