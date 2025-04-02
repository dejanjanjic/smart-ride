package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.beans.UserBean;
import net.etfbl.ip.smartrideclient.dto.User;
import net.etfbl.ip.smartrideclient.util.ConnectionPool;
import net.etfbl.ip.smartrideclient.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM user WHERE username=? AND password=?";
    public static User selectByUsernameAndPassword(String username, String password){
        User user = null;
        Connection connection = null;
        ResultSet rs = null;
        Object[] values = {username, password};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()){
                user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return user;
    }
}
