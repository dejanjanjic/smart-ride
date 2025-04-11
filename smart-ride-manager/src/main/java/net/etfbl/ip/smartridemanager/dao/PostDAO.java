package net.etfbl.ip.smartridemanager.dao;

import net.etfbl.ip.smartridemanager.dto.Post;
import net.etfbl.ip.smartridemanager.util.ConnectionPool;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_FIND_ALL = "SELECT id, title, content, created_at FROM post ORDER BY created_at DESC";
    private static final String SQL_SEARCH = "SELECT id, title, content, created_at FROM post WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC";
    private static final String SQL_INSERT = "INSERT INTO post (title, content, created_at) VALUES (?, ?, ?)";

    public static List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_FIND_ALL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    post.setCreatedAt(ts.toLocalDateTime());
                }
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, connection);
        }
        return posts;
    }

    public static List<Post> search(String query) {
        List<Post> posts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String searchQuery = "%" + query + "%";
        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_SEARCH);
            pstmt.setString(1, searchQuery);
            pstmt.setString(2, searchQuery);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    post.setCreatedAt(ts.toLocalDateTime());
                }
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, connection);
        }
        return posts;
    }

    public static boolean insert(Post post) {
        boolean success = false;
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_INSERT);
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            if (post.getCreatedAt() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(post.getCreatedAt()));
            } else {
                pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }
            int affectedRows = pstmt.executeUpdate();
            success = (affectedRows == 1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, pstmt, connection);
        }
        return success;
    }

    private static void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) connectionPool.checkIn(conn);
    }
}