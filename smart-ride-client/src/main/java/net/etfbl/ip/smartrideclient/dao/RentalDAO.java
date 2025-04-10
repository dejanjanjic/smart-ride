package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.dto.Rental;
import net.etfbl.ip.smartrideclient.util.ConnectionPool;
import net.etfbl.ip.smartrideclient.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_INSERT_RENTAL =
            "INSERT INTO rental (active, date_time, duration_in_seconds, vehicle_id, client_id, price, " +
                    "end_locationx, end_locationy, start_locationx, start_locationy) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_CHECK_ACTIVE_RENTAL =
            "SELECT COUNT(*) FROM rental WHERE vehicle_id = ? AND active = true;";
    private static final String SQL_FIND_ACTIVE_RENTAL_BY_ID_AND_USER_ID =
            "SELECT id, active, date_time, vehicle_id, client_id, start_locationx, start_locationy " + // Ispravljena imena kolona
                    "FROM rental " +
                    "WHERE id = ? AND client_id = ? AND active = TRUE";
    private static final String SQL_UPDATE_RENTAL_END =
            "UPDATE rental " +
                    "SET active = ?, duration_in_seconds = ?, price = ?, end_locationx = ?, end_locationy = ? " +
                    "WHERE id = ?";
    private static final String SQL_SELECT_RENTAL_HISTORY =
            "SELECT id, vehicle_id, date_time, duration_in_seconds, price " +
                    "FROM rental WHERE client_id = ? AND active = FALSE ORDER BY date_time DESC";

    public static List<Rental> getRentalHistoryForUser(Long userId) {
        List<Rental> history = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_SELECT_RENTAL_HISTORY);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getLong("id"));
                rental.setVehicleId(rs.getString("vehicle_id"));
                Timestamp startTimeStamp = rs.getTimestamp("date_time");
                if (startTimeStamp != null) {
                    rental.setDateTime(startTimeStamp.toLocalDateTime());
                }
                rental.setDurationInSeconds(rs.getInt("duration_in_seconds"));
                rental.setPrice(rs.getBigDecimal("price"));
                rental.setActive(false);
                history.add(rental);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching rental history for user ID: " + userId);
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            connectionPool.checkIn(connection);
        }
        return history;
    }

    public static Long addRental(Rental rental) {
        Long generatedId = null;
        Connection connection = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rsCheck = null;
        ResultSet generatedKeys = null;

        try {
            connection = connectionPool.checkOut();

            checkStmt = connection.prepareStatement(SQL_CHECK_ACTIVE_RENTAL);
            checkStmt.setString(1, rental.getVehicleId());
            rsCheck = checkStmt.executeQuery();

            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                System.err.println("RentalDAO: Vehicle " + rental.getVehicleId() + " is already actively rented.");
                return null;
            }

            insertStmt = connection.prepareStatement(SQL_INSERT_RENTAL, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setBoolean(1, rental.isActive());
            insertStmt.setTimestamp(2, java.sql.Timestamp.valueOf(rental.getDateTime()));
            insertStmt.setInt(3, rental.getDurationInSeconds());
            insertStmt.setString(4, rental.getVehicleId());
            insertStmt.setLong(5, rental.getClientId());
            if (rental.getPrice() != null) {
                insertStmt.setBigDecimal(6, rental.getPrice());
            } else {
                insertStmt.setNull(6, java.sql.Types.DECIMAL);
            }
            if (rental.getEndLocationX() != 0.0) {
                insertStmt.setDouble(7, rental.getEndLocationX());
                insertStmt.setDouble(8, rental.getEndLocationY());
            } else {
                insertStmt.setNull(7, java.sql.Types.DOUBLE);
                insertStmt.setNull(8, java.sql.Types.DOUBLE);
            }
            insertStmt.setDouble(9, rental.getStartLocationX());
            insertStmt.setDouble(10, rental.getStartLocationY());

            int affectedRows = insertStmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1);
                    System.out.println("RentalDAO: Inserted rental with generated ID: " + generatedId);
                } else {
                    System.err.println("RentalDAO: Inserted rental but failed to retrieve generated ID.");
                    throw new SQLException("Creating rental failed, no ID obtained.");
                }
            } else {
                System.err.println("RentalDAO: Insert statement affected 0 rows.");
            }

        } catch (SQLException e) {
            System.err.println("RentalDAO: SQLException occurred during addRental.");
            e.printStackTrace();
        } finally {
            try { if (generatedKeys != null) generatedKeys.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (rsCheck != null) rsCheck.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (checkStmt != null) checkStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (insertStmt != null) insertStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            connectionPool.checkIn(connection);
        }

        return generatedId;
    }

    public static Rental findActiveRentalByIdAndUserId(Long rentalId, Long userId) {
        Rental rental = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_FIND_ACTIVE_RENTAL_BY_ID_AND_USER_ID);
            pstmt.setLong(1, rentalId);
            pstmt.setLong(2, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                rental = new Rental();
                rental.setId(rs.getLong("id"));
                rental.setActive(rs.getBoolean("active"));
                Timestamp startTimeStamp = rs.getTimestamp("date_time");
                if(startTimeStamp != null) {
                    rental.setDateTime(startTimeStamp.toLocalDateTime());
                }
                rental.setVehicleId(rs.getString("vehicle_id"));
                rental.setClientId(rs.getLong("client_id"));
                rental.setStartLocationX(rs.getDouble("start_locationx"));
                rental.setStartLocationY(rs.getDouble("start_locationy"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding active rental for rentalId=" + rentalId + ", userId=" + userId);
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            connectionPool.checkIn(connection);
        }

        return rental;
    }

    public static boolean updateRentalEndDetails(Long rentalId, int durationSeconds, BigDecimal finalPrice, Double endLocationX, Double endLocationY) {
        boolean success = false;
        Connection connection = null;
        PreparedStatement pstmt = null;

        if (finalPrice == null) {
            finalPrice = BigDecimal.ZERO;
            System.err.println("Warning: finalPrice was null in updateRentalEndDetails for rentalId=" + rentalId + ". Setting to 0.");
        }


        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_UPDATE_RENTAL_END);

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, durationSeconds);
            pstmt.setBigDecimal(3, finalPrice);
            if (endLocationX != null) {
                pstmt.setDouble(4, endLocationX);
            } else {
                pstmt.setNull(4, java.sql.Types.DOUBLE);
            }
            if (endLocationY != null) {
                pstmt.setDouble(5, endLocationY);
            } else {
                pstmt.setNull(5, java.sql.Types.DOUBLE);
            }
            pstmt.setLong(6, rentalId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 1) {
                success = true;
                System.out.println("RentalDAO: Successfully updated end details for rental ID: " + rentalId);
            } else if (affectedRows == 0) {
                System.err.println("RentalDAO: Failed to update end details. Rental ID: " + rentalId + " not found or not active.");
            } else {
                System.err.println("RentalDAO: WARNING - More than one row updated for rental ID: " + rentalId);
                success = true;
            }

        } catch (SQLException e) {
            System.err.println("RentalDAO: SQLException occurred during updateRentalEndDetails for rentalId=" + rentalId);
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            connectionPool.checkIn(connection);
        }

        return success;
    }
}
