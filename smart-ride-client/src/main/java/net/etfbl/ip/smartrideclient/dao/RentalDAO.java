package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.dto.Rental;
import net.etfbl.ip.smartrideclient.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RentalDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_INSERT_RENTAL =
            "INSERT INTO rental (active, date_time, duration_in_seconds, vehicle_id, client_id, price, " +
                    "end_locationx, end_locationy, start_locationx, start_locationy) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static boolean addRental(Rental rental) {
        boolean success = false;
        Connection connection = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT_RENTAL);
            pstmt.setBoolean(1, rental.isActive());
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(rental.getDateTime()));
            pstmt.setInt(3, rental.getDurationInSeconds());
            pstmt.setString(4, rental.getVehicleId());
            pstmt.setLong(5, rental.getClientId());
            pstmt.setBigDecimal(6, rental.getPrice());
            pstmt.setDouble(7, rental.getEndLocationX());
            pstmt.setDouble(8, rental.getEndLocationY());
            pstmt.setDouble(9, rental.getStartLocationX());
            pstmt.setDouble(10, rental.getStartLocationY());

            success = pstmt.executeUpdate() > 0;
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return success;
    }
}
