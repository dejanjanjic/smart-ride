package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.dto.Bike;
import net.etfbl.ip.smartrideclient.dto.Scooter;
import net.etfbl.ip.smartrideclient.util.ConnectionPool;
import net.etfbl.ip.smartrideclient.util.DBUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class BikeDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_AVAILABLE_BIKES =
            "SELECT b.id AS bike_id, m.name AS manufacturer_name, v.model, b.max_range, v.picture_path " +
                    "FROM ebike b " +
                    "JOIN vehicle v ON b.id = v.id " +
                    "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                    "WHERE NOT EXISTS ( " +
                    "    SELECT 1 FROM rental r " +
                    "    WHERE r.vehicle_id = b.id AND r.active = TRUE " +
                    ") " +
                    "AND NOT EXISTS ( " +
                    "    SELECT 1 FROM failure f " +
                    "    WHERE f.vehicle_id = b.id " +
                    ");";
    private static final String SQL_SELECT_BIKE_BY_ID =
            "SELECT b.id AS bike_id, m.name AS manufacturer_name, v.model, b.max_range " +
                    "FROM ebike b " +
                    "JOIN vehicle v ON b.id = v.id " +
                    "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                    "WHERE b.id = ?;";
    public static List<Bike> selectAvailableBikes() {
        List<Bike> bikes = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_SELECT_AVAILABLE_BIKES, false);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Bike bike = new Bike();
                bike.setId(rs.getString("bike_id"));
                bike.setManufacturerName(rs.getString("manufacturer_name"));
                bike.setModel(rs.getString("model"));
                bike.setMaxRange(rs.getInt("max_range"));
                bikes.add(bike);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return bikes;
    }

    public static Bike getBikeById(String bikeId) {
        Bike bike = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_SELECT_BIKE_BY_ID, false);
            pstmt.setString(1, bikeId);
            rs = pstmt.executeQuery();
            String imagePath = null;
            if (rs.next()) {
                bike = new Bike();
                bike.setId(rs.getString("bike_id"));
                bike.setManufacturerName(rs.getString("manufacturer_name"));
                bike.setModel(rs.getString("model"));
                imagePath=rs.getString("picture_path");
                if(imagePath != null && !imagePath.isEmpty()){
                    byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                    String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                    bike.setImage(imageBase64);
                } else{
                    bike.setImage(null);
                }
                bike.setMaxRange(rs.getInt("max_range"));
            }

            pstmt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return bike;
    }
}
