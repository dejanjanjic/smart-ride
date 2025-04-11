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
            "  c.image,\n" +
            "  u.first_name,\n" +
            "  u.last_name,\n" +
            "  u.username,\n" +
            "  c.blocked\n" +
            "FROM client c\n" +
            "JOIN user u ON c.id = u.id\n WHERE u.username=? AND u.password=?;";
    private static final String SQL_UPDATE_AVATAR = "UPDATE client SET image=? WHERE id=?;";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    private static final String SQL_EXISTS_BY_EMAIL = "SELECT 1 FROM client WHERE email = ? LIMIT 1;";
    private static final String SQL_EXISTS_BY_USERNAME = "SELECT 1 FROM user WHERE username = ? LIMIT 1;";

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
                        rs.getString("image"),
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


    public static boolean updateUserAvatar(long userId, String imagePath) {
        Connection connection = null;
        boolean success = false;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_UPDATE_AVATAR, false, imagePath, userId);
            int affectedRows = pstmt.executeUpdate();
            success = affectedRows > 0;
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return success;
    }

    public static boolean updatePassword(Long userId, String password) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_UPDATE_PASSWORD);
            pstmt.setString(1, password);
            pstmt.setLong(2, userId);
            int affectedRows = pstmt.executeUpdate();
            success = (affectedRows == 1);
        } catch (SQLException e) {
            System.err.println("Error updating password for user ID: " + userId);
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            connectionPool.checkIn(connection);
        }
        return success;
    }
    public static boolean existsByUsername(String username) {
        Connection connection = null;
        boolean exists = false;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection, SQL_EXISTS_BY_USERNAME, false, username);
            ResultSet rs = pstmt.executeQuery();
            exists = rs.next();
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return exists;
    }

    public static boolean existsByEmail(String email) {
        Connection connection = null;
        boolean exists = false;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection, SQL_EXISTS_BY_EMAIL, false, email);
            ResultSet rs = pstmt.executeQuery();
            exists = rs.next();
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return exists;
    }

    public static boolean addNewClient(User user, String password) {
        Connection connection = null;
        boolean success = false;
        String SQL_INSERT_USER = "INSERT INTO user (first_name, last_name, username, password) VALUES (?, ?, ?, ?);";
        String SQL_INSERT_CLIENT = "INSERT INTO client (id, email, phone_number, image, blocked) VALUES (?, ?, ?, ?, ?);";

        try {
            connection = connectionPool.checkOut();
            connection.setAutoCommit(false); // Start transaction

            // Insert into `user` table
            PreparedStatement pstmtUser = DBUtil.prepareStatement(connection, SQL_INSERT_USER, true,
                    user.getFirstName(), user.getLastName(), user.getUsername(), password);
            pstmtUser.executeUpdate();

            // Retrieve generated ID for `user`
            ResultSet generatedKeys = pstmtUser.getGeneratedKeys();
            long userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            }
            pstmtUser.close();

            if (userId == -1) {
                throw new SQLException("Failed to retrieve generated user ID.");
            }

            // Insert into `client` table
            PreparedStatement pstmtClient = DBUtil.prepareStatement(connection, SQL_INSERT_CLIENT, false,
                    userId, user.getEmail(), user.getPhoneNumber(), null, false);
            pstmtClient.executeUpdate();
            pstmtClient.close();

            connection.commit(); // Commit transaction
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Reset auto-commit mode
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            connectionPool.checkIn(connection);
        }

        return success;
    }

}
