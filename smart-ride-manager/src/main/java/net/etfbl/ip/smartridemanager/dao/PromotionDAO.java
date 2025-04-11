package net.etfbl.ip.smartridemanager.dao;

import net.etfbl.ip.smartridemanager.dto.Promotion;
import net.etfbl.ip.smartridemanager.util.ConnectionPool;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_FIND_ALL = "SELECT id, title, description, created_at, valid_until FROM promotion ORDER BY created_at DESC";
    private static final String SQL_SEARCH = "SELECT id, title, description, created_at, valid_until FROM promotion WHERE title LIKE ? OR description LIKE ? ORDER BY created_at DESC";
    private static final String SQL_INSERT = "INSERT INTO promotion (title, description, created_at, valid_until) VALUES (?, ?, ?, ?)";

    public static List<Promotion> findAll() {
        List<Promotion> promotions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_FIND_ALL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setId(rs.getLong("id"));
                promotion.setTitle(rs.getString("title"));
                promotion.setDescription(rs.getString("description"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    promotion.setCreatedAt(ts.toLocalDateTime());
                }
                Date sqlDate = rs.getDate("valid_until");
                if (sqlDate != null) {
                    promotion.setValidUntil(sqlDate.toLocalDate());
                }
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, connection);
        }
        return promotions;
    }

    public static List<Promotion> search(String query) {
        List<Promotion> promotions = new ArrayList<>();
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
                Promotion promotion = new Promotion();
                promotion.setId(rs.getLong("id"));
                promotion.setTitle(rs.getString("title"));
                promotion.setDescription(rs.getString("description"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    promotion.setCreatedAt(ts.toLocalDateTime());
                }
                Date sqlDate = rs.getDate("valid_until");
                if (sqlDate != null) {
                    promotion.setValidUntil(sqlDate.toLocalDate());
                }
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, connection);
        }
        return promotions;
    }

    public static boolean insert(Promotion promotion) {
        boolean success = false;
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_INSERT);
            pstmt.setString(1, promotion.getTitle());
            pstmt.setString(2, promotion.getDescription());
            if (promotion.getCreatedAt() != null) {
                pstmt.setTimestamp(3, Timestamp.valueOf(promotion.getCreatedAt()));
            } else {
                pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }
            if (promotion.getValidUntil() != null) {
                pstmt.setDate(4, Date.valueOf(promotion.getValidUntil()));
            } else {
                pstmt.setNull(4, Types.DATE);
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