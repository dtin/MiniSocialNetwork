/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblPosts;

/**
 *
 * @author Tin
 */
public class TblPostsDetailError {
    private String notFoundPost;

    public TblPostsDetailError() {
    }

    public TblPostsDetailError(String notFoundPost) {
        this.notFoundPost = notFoundPost;
    }

    public String getNotFoundPost() {
        return notFoundPost;
    }

    public void setNotFoundPost(String notFoundPost) {
        this.notFoundPost = notFoundPost;
    }
}
