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


}
