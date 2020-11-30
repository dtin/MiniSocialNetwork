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
public class TblUsersActivationError {
    private String sendEmailFail;
    private String updateActivationCodeFail;
    private String activationCodeNotCorrect;

    public TblUsersActivationError() {
    }

    public TblUsersActivationError(String sendEmailFail, String updateActivationCodeFail, String activationCodeNotCorrect) {
        this.sendEmailFail = sendEmailFail;
        this.updateActivationCodeFail = updateActivationCodeFail;
        this.activationCodeNotCorrect = activationCodeNotCorrect;
    }

    public String getSendEmailFail() {
        return sendEmailFail;
    }

    public void setSendEmailFail(String sendEmailFail) {
        this.sendEmailFail = sendEmailFail;
    }

    public String getUpdateActivationCodeFail() {
        return updateActivationCodeFail;
    }

    public void setUpdateActivationCodeFail(String updateActivationCodeFail) {
        this.updateActivationCodeFail = updateActivationCodeFail;
    }

    public String getActivationCodeNotCorrect() {
        return activationCodeNotCorrect;
    }

    public void setActivationCodeNotCorrect(String activationCodeNotCorrect) {
        this.activationCodeNotCorrect = activationCodeNotCorrect;
    }
    
    
    
    
}
