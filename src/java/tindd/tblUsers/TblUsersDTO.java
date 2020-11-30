/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblUsers;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblUsersDTO implements Serializable {

    private String email;
    private String name;
    private String password;
    private String role;
    private int statusId;

    public TblUsersDTO() {
    }

    public TblUsersDTO(String email, String name, String password, String role, int statusId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.statusId = statusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

}
