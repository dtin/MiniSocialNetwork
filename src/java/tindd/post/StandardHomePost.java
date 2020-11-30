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
public class StandardHomePost implements Serializable{
    private TblPostsDTO postsDTO;
    private String userFullName;
    private String createdAtFull;

    public StandardHomePost() {
    }

    public StandardHomePost(TblPostsDTO postsDTO, String userFullName, String createdAtFull) {
        this.postsDTO = postsDTO;
        this.userFullName = userFullName;
        this.createdAtFull = createdAtFull;
    }

    public TblPostsDTO getPostsDTO() {
        return postsDTO;
    }

    public void setPostsDTO(TblPostsDTO postsDTO) {
        this.postsDTO = postsDTO;
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
