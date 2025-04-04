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
    private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "SELECT\n" +
            "  c.id,\n" +
            "  c.email,\n" +
            "  c.phone_number,\n" +
            "  u.first_name,\n" +
            "  u.last_name,\n" +
            "  u.username,\n" +
            "  c.blocked\n" +
            "FROM client c\n" +
            "JOIN user u ON c.id = u.id\n WHERE u.username=? AND u.password=?;";
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
                user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getBoolean("blocked")
                );
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
