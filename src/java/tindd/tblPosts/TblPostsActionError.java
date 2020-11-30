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
public class TblPostsActionError {
    private String emptyTitle;
    private String emptyPost;
    private String postFail;
    private String notAuthorizedToDelete;
    private String deleteFail;
    
    public TblPostsActionError() {
    }

    public TblPostsActionError(String emptyTitle, String emptyPost, String postFail, String notAuthorizedToDelete, String deleteFail) {
        this.emptyTitle = emptyTitle;
        this.emptyPost = emptyPost;
        this.postFail = postFail;
        this.notAuthorizedToDelete = notAuthorizedToDelete;
        this.deleteFail = deleteFail;
    }

    public String getEmptyTitle() {
        return emptyTitle;
    }

    public void setEmptyTitle(String emptyTitle) {
        this.emptyTitle = emptyTitle;
    }

    public String getEmptyPost() {
        return emptyPost;
    }

    public void setEmptyPost(String emptyPost) {
        this.emptyPost = emptyPost;
    }

    public String getPostFail() {
        return postFail;
    }

    public void setPostFail(String postFail) {
        this.postFail = postFail;
    }

    public String getNotAuthorizedToDelete() {
        return notAuthorizedToDelete;
    }

    public void setNotAuthorizedToDelete(String notAuthorizedToDelete) {
        this.notAuthorizedToDelete = notAuthorizedToDelete;
    }

    public String getDeleteFail() {
        return deleteFail;
    }

    public void setDeleteFail(String deleteFail) {
        this.deleteFail = deleteFail;
    }
    
}
