/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.comment;

import java.io.Serializable;
import tindd.tblComments.TblCommentsDTO;

/**
 *
 * @author Tin
 */
public class StandardComment implements Serializable{
    private TblCommentsDTO commentsDTO;
    private String userFullName;
    private String createdAtFull;

    public StandardComment() {
    }

    public StandardComment(TblCommentsDTO commentsDTO, String userFullName, String createdAtFull) {
        this.commentsDTO = commentsDTO;
        this.userFullName = userFullName;
        this.createdAtFull = createdAtFull;
    }

    public TblCommentsDTO getCommentsDTO() {
        return commentsDTO;
    }

    public void setCommentsDTO(TblCommentsDTO commentsDTO) {
        this.commentsDTO = commentsDTO;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getCreatedAtFull() {
        return createdAtFull;
    }

    public void setCreatedAtFull(String createdAtFull) {
        this.createdAtFull = createdAtFull;
    }
}
