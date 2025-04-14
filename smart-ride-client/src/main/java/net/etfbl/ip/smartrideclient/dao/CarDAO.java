package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.dto.Bike;
import net.etfbl.ip.smartrideclient.dto.Car;
import net.etfbl.ip.smartrideclient.util.ConnectionPool;
import net.etfbl.ip.smartrideclient.util.DBUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CarDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_AVAILABLE_CARS =
            "SELECT c.id AS car_id, m.name AS manufacturer_name, v.model, v.picture_path " +
                    "FROM car c " +
                    "JOIN vehicle v ON c.id = v.id " +
                    "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                    "WHERE NOT EXISTS ( " +
                    "    SELECT 1 FROM rental r " +
                    "    WHERE r.vehicle_id = c.id AND r.active = TRUE " +
                    ") " +
                    "AND NOT EXISTS ( " +
                    "    SELECT 1 FROM failure f " +
                    "    WHERE f.vehicle_id = c.id " +
                    ");";
    private static final String SQL_SELECT_CAR_BY_ID =
            "SELECT c.id AS car_id, m.name AS manufacturer_name, v.model " +
                    "FROM car c " +
                    "JOIN vehicle v ON c.id = v.id " +
                    "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                    "WHERE c.id = ?;";
    public static List<Car> selectAvailableCars() {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_SELECT_AVAILABLE_CARS, false);
            rs = pstmt.executeQuery();
            String imagePath = null;
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getString("car_id"));
                car.setManufacturerName(rs.getString("manufacturer_name"));
                car.setModel(rs.getString("model"));
                imagePath=rs.getString("picture_path");
                if(imagePath != null && !imagePath.isEmpty()){
                    byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                    String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                    car.setImage(imageBase64);
                } else{
                    car.setImage(null);
                }
                cars.add(car);
            }
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return cars;
    }

    public static Car getCarById(String carId) {
        Car car = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DBUtil.prepareStatement(connection,
                    SQL_SELECT_CAR_BY_ID, false);
            pstmt.setString(1, carId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                car = new Car();
                car.setId(rs.getString("car_id"));
                car.setManufacturerName(rs.getString("manufacturer_name"));
                car.setModel(rs.getString("model"));
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return car;
    }
}
