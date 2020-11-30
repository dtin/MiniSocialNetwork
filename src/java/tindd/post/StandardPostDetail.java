/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.post;

import java.io.Serializable;
import tindd.tblPosts.TblPostsDTO;

/**
 *
 * @author Tin
 */
public class StandardPostDetail extends StandardHomePost implements Serializable{
    
    private String emotion;
    private int countLike;
    private int countDislike;
    private int countComment;

    public StandardPostDetail() {
    }

    public StandardPostDetail(String emotion, int countLike, int countDislike, int countComment, TblPostsDTO postsDTO, String userFullName, String createdAtFull) {
        super(postsDTO, userFullName, createdAtFull);
        this.emotion = emotion;
        this.countLike = countLike;
        this.countDislike = countDislike;
        this.countComment = countComment;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public int getCountDislike() {
        return countDislike;
    }

    public void setCountDislike(int countDislike) {
        this.countDislike = countDislike;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }
}
