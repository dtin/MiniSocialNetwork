/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblComments;

/**
 *
 * @author Tin
 */
public class TblCommentsError {
    private String addCommentError;
    private String notAuthorizedToDelete;

    public TblCommentsError() {
    }

    public TblCommentsError(String addCommentError, String notAuthorizedToDelete) {
        this.addCommentError = addCommentError;
        this.notAuthorizedToDelete = notAuthorizedToDelete;
    }

    public String getAddCommentError() {
        return addCommentError;
    }

    public void setAddCommentError(String addCommentError) {
        this.addCommentError = addCommentError;
    }

    public String getNotAuthorizedToDelete() {
        return notAuthorizedToDelete;
    }

    public void setNotAuthorizedToDelete(String notAuthorizedToDelete) {
        this.notAuthorizedToDelete = notAuthorizedToDelete;
    }
}
