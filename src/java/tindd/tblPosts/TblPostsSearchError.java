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
public class TblPostsSearchError {
    private String searchValueNotFound;

    public TblPostsSearchError() {
    }

    public TblPostsSearchError(String searchValueNotFound) {
        this.searchValueNotFound = searchValueNotFound;
    }

    public String getSearchValueNotFound() {
        return searchValueNotFound;
    }

    public void setSearchValueNotFound(String searchValueNotFound) {
        this.searchValueNotFound = searchValueNotFound;
    }
    
    
}
