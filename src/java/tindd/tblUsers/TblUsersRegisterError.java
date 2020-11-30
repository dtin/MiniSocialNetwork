/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblUsers;

/**
 *
 * @author Tin
 */
public class TblUsersRegisterError {
    private String duplicateEmail;
    private String formatEmail;
    private String lengthPassword;
    private String confirmPassword;
    private String emptyConfirm;
    private String lengthName;

    public TblUsersRegisterError() {
    }

    public TblUsersRegisterError(String duplicateEmail, String formatEmail, String lengthPassword, String confirmPassword, String emptyConfirm, String lengthName) {
        this.duplicateEmail = duplicateEmail;
        this.formatEmail = formatEmail;
        this.lengthPassword = lengthPassword;
        this.confirmPassword = confirmPassword;
        this.emptyConfirm = emptyConfirm;
        this.lengthName = lengthName;
    }

    public String getDuplicateEmail() {
        return duplicateEmail;
    }

    public void setDuplicateEmail(String duplicateEmail) {
        this.duplicateEmail = duplicateEmail;
    }

    public String getFormatEmail() {
        return formatEmail;
    }

    public void setFormatEmail(String formatEmail) {
        this.formatEmail = formatEmail;
    }

    public String getLengthPassword() {
        return lengthPassword;
    }

    public void setLengthPassword(String lengthPassword) {
        this.lengthPassword = lengthPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmptyConfirm() {
        return emptyConfirm;
    }

    public void setEmptyConfirm(String emptyConfirm) {
        this.emptyConfirm = emptyConfirm;
    }

    public String getLengthName() {
        return lengthName;
    }

    public void setLengthName(String lengthName) {
        this.lengthName = lengthName;
    }
    
}
