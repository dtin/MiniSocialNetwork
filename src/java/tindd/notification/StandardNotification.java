/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.notification;

import java.io.Serializable;
import tindd.tblNotifications.TblNotificationsDTO;

/**
 *
 * @author Tin
 */
public class StandardNotification implements Serializable{
    private TblNotificationsDTO notificationsDTO;
    private String dateAtFull;

    public StandardNotification() {
    }

    public StandardNotification(TblNotificationsDTO notificationsDTO, String dateAtFull) {
        this.notificationsDTO = notificationsDTO;
        this.dateAtFull = dateAtFull;
    }

    public TblNotificationsDTO getNotificationsDTO() {
        return notificationsDTO;
    }

    public void setNotificationsDTO(TblNotificationsDTO notificationsDTO) {
        this.notificationsDTO = notificationsDTO;
    }

    public String getDateAtFull() {
        return dateAtFull;
    }

    public void setDateAtFull(String dateAtFull) {
        this.dateAtFull = dateAtFull;
    }
}
