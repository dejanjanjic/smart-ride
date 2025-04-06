package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalPriceConfigDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String GET_PRICE_FOR_SCOOTER =
            "SELECT price FROM rental_price_config WHERE vehicle_type = 'E_SCOOTER'";

    public static double getScooterPricePerMinute() {
        double price = 0.0;
        Connection connection = null;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement stmt = connection.prepareStatement(GET_PRICE_FOR_SCOOTER);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("price");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return price;
    }
}
