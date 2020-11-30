/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblEmotions;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblEmotionsDTO implements Serializable {
    private int postId;
    private String emotionUserId;
    private String emotion;
    private boolean isDeleted;
    private long createdAt;
    private long updatedAt;

    public TblEmotionsDTO() {
    }

    public TblEmotionsDTO(int postId, String emotionUserId, String emotion, boolean isDeleted, long createdAt, long updatedAt) {
        this.postId = postId;
        this.emotionUserId = emotionUserId;
        this.emotion = emotion;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getEmotionUserId() {
        return emotionUserId;
    }

    public void setEmotionUserId(String emotionUserId) {
        this.emotionUserId = emotionUserId;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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
    
    
}
