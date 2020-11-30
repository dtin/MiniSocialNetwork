package tindd.tblStatus;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tin
 */
public class TblStatusDAO implements Serializable {

    public int getStatusId(String name)
            throws NamingException, SQLException {

        int statusId = -1;

        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT statusId "
                        + "FROM tblStatus "
                        + "WHERE name=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, name);
                rs = stm.executeQuery();
                if (rs.next()) {
                    statusId = rs.getInt("statusId");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return statusId;
    }
    
    public String getStatusName(int statusId) 
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String result = null;
        
        try {
            conn = DBHelper.makeConnection();
            if(conn != null) {
                String sql = "SELECT name "
                        + "FROM tblStatus "
                        + "WHERE statusId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, statusId);
                
                rs = stm.executeQuery();
                if(rs.next()) {
                    result = rs.getString("name");
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            
            if(stm != null) {
                stm.close();
            }
            
            if(conn != null) {
                conn.close();
            }
        }
        
        return result;
    }
}
