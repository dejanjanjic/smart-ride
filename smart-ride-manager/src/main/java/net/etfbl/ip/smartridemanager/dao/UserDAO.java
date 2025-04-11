package net.etfbl.ip.smartridemanager.dao;

import net.etfbl.ip.smartridemanager.dto.User;
import net.etfbl.ip.smartridemanager.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    public static User findManagerUserByUsernameAndPassword(String username, String password) {
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id, username, password, role, first_name, last_name FROM user WHERE username = ? AND password = ? AND role = 'MANAGEMENT'";
        try {
            conn = connectionPool.checkOut();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            connectionPool.checkIn(conn);
        }
        return user;
    }
}
