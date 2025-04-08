package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.dto.Location;
import net.etfbl.ip.smartrideclient.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_LOCATION_BY_ID = "SELECT positionx, positiony FROM `smart-ride`.vehicle v WHERE v.id=?;";
    public static Location getLocation(String vehicleId) {
        Location location = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = connection.prepareStatement(SQL_SELECT_LOCATION_BY_ID);
            pstmt.setString(1, vehicleId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                double positionX = rs.getDouble("positionx");
                double positionY = rs.getDouble("positiony");
                location = new Location(positionX, positionY);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return location;
    }
}
