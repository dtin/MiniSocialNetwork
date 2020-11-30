/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblNotifications;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblNotificationsDTO implements Serializable {
    private int notificationId;
    private int postId;
    private int commentId;
    private String notiUserId;
    private String fromUserId;
    private String typeOfNoti;
    private String contentNoti;
    private long createdAt;
    private long updatedAt;
    private boolean isDeleted;

    public TblNotificationsDTO() {
    }

    public TblNotificationsDTO(int notificationId, int postId, int commentId, String notiUserId, String fromUserId, String typeOfNoti, String contentNoti, long createdAt, long updatedAt, boolean isDeleted) {
        this.notificationId = notificationId;
        this.postId = postId;
        this.commentId = commentId;
        this.notiUserId = notiUserId;
        this.fromUserId = fromUserId;
        this.typeOfNoti = typeOfNoti;
        this.contentNoti = contentNoti;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getNotiUserId() {
        return notiUserId;
    }

    public void setNotiUserId(String notiUserId) {
        this.notiUserId = notiUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getTypeOfNoti() {
        return typeOfNoti;
    }

    public void setTypeOfNoti(String typeOfNoti) {
        this.typeOfNoti = typeOfNoti;
    }

    public String getContentNoti() {
        return contentNoti;
    }

    public void setContentNoti(String contentNoti) {
        this.contentNoti = contentNoti;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
