package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.dto.Scooter;
import net.etfbl.ip.smartrideclient.util.ConnectionPool;
import net.etfbl.ip.smartrideclient.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScooterDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_AVAILABLE_SCOOTERS =
            "SELECT s.id AS scooter_id, m.name AS manufacturer_name, v.model, s.max_speed " +
                    "FROM escooter s " +
                    "JOIN vehicle v ON s.id = v.id " +
                    "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                    "WHERE NOT EXISTS ( " +
                    "    SELECT 1 FROM rental r " +
                    "    WHERE r.vehicle_id = s.id AND r.active = TRUE " +
                    ") " +
                    "AND NOT EXISTS ( " +
                    "    SELECT 1 FROM failure f " +
                    "    WHERE f.vehicle_id = s.id " +
                    ");";
    private static final String SQL_SELECT_SCOOTER_BY_ID =
            "SELECT s.id AS scooter_id, m.name AS manufacturer_name, v.model, s.max_speed " +
                    "FROM escooter s " +
                    "JOIN vehicle v ON s.id = v.id " +
                    "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                    "WHERE s.id = ?;";
    private static final String SQL_UPDATE_VEHICLE_LOCATION =
            "UPDATE vehicle SET positionx = ?, positiony = ? WHERE id = ?;";

    public static List<Scooter> selectAvailableScooters() {
        List<Scooter> scooters = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_SELECT_AVAILABLE_SCOOTERS, false);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Scooter scooter = new Scooter();
                scooter.setId(rs.getString("scooter_id"));
                scooter.setManufacturerName(rs.getString("manufacturer_name"));
                scooter.setModel(rs.getString("model"));
                scooter.setMaxSpeed(rs.getInt("max_speed"));
                scooters.add(scooter);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return scooters;
    }

    public static Scooter getScooterById(String id) {
        Scooter scooter = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_SELECT_SCOOTER_BY_ID, false);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                scooter = new Scooter();
                scooter.setId(rs.getString("scooter_id"));
                scooter.setManufacturerName(rs.getString("manufacturer_name"));
                scooter.setModel(rs.getString("model"));
                scooter.setMaxSpeed(rs.getInt("max_speed"));
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return scooter;
    }

    public static void updateLocation(String vehicleId, double latitude, double longitude) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = connectionPool.checkOut();
            pstmt = connection.prepareStatement(SQL_UPDATE_VEHICLE_LOCATION);

            pstmt.setDouble(1, latitude);
            pstmt.setDouble(2, longitude);
            pstmt.setString(3, vehicleId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Successfully updated location for vehicle ID: " + vehicleId + " to (" + latitude + ", " + longitude + ")");
            } else {
                System.err.println("Could not update location. Vehicle with ID: " + vehicleId + " not found.");
            }
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error updating location for vehicle ID: " + vehicleId);
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
    }
}
