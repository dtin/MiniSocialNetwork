/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblUsers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tindd.tblStatus.TblStatusDAO;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblUsersDAO implements Serializable {

    public TblUsersDTO checkLogin(String email, String encryptedPass)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblUsersDTO dto = null;

        try {
            conn = DBHelper.makeConnection();

            if (conn != null) {
                String sql = "SELECT name, role, statusId "
                        + "FROM tblUsers "
                        + "WHERE email=? AND password=?";
                stm = conn.prepareStatement(sql);

                stm.setString(1, email);
                stm.setString(2, encryptedPass);
                rs = stm.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    if (role.equals("Member")) {
                        String name = rs.getString("name");
                        int statusId = rs.getInt("statusId");
                        dto = new TblUsersDTO(email, name, encryptedPass, role, statusId);
                    }
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

        return dto;
    }

    public boolean updateActivationCode(int activationCode, String email)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblUsers "
                        + "SET activationCode=? "
                        + "WHERE email=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, activationCode);
                stm.setString(2, email);

                int rowCount = stm.executeUpdate();
                if (rowCount != 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public int getActivationCode(String email)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int result = -1;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT activationCode "
                        + "FROM tblUsers "
                        + "WHERE email=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);

                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("activationCode");
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

        return result;
    }

    public boolean updateUserStatus(String email, int statusId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblUsers "
                        + "SET statusId=? "
                        + "WHERE email=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, statusId);
                stm.setString(2, email);
                int rowCount = stm.executeUpdate();
                if (rowCount != 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean createAccount(String email, String encryptedPass, String name, int activationCode)
            throws SQLException, NamingException {
        boolean result = false;

        Connection conn = null;
        PreparedStatement stm = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                TblStatusDAO dao = new TblStatusDAO();
                int statusId = dao.getStatusId("New");

                if (statusId != -1) {
                    String sql = "INSERT INTO tblUsers (email, password, name, role, statusId, activationCode) "
                            + "VALUES(?, ?, ?, ?, ?, ?)";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, email);
                    stm.setString(2, encryptedPass);
                    stm.setString(3, name);
                    stm.setString(4, "Member");
                    stm.setInt(5, statusId);
                    stm.setInt(6, activationCode);

                    int rowCount = stm.executeUpdate();
                    if (rowCount != 0) {
                        result = true;
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public String getUserFullName(String email)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String name = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT name FROM tblUsers WHERE email=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    name = rs.getString("name");
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

        return name;
    }
}
