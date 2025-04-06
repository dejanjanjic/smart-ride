package net.etfbl.ip.smartrideclient.dao;

import net.etfbl.ip.smartrideclient.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalPriceConfigDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String GET_PRICE_FOR_SCOOTER = "SELECT price FROM rental_price_config WHERE vehicle_type = 'E_SCOOTER'";
    private static final String GET_PRICE_FOR_CAR = "SELECT price FROM rental_price_config WHERE vehicle_type = 'CAR'";
    private static final String GET_PRICE_FOR_BIKE = "SELECT price FROM rental_price_config WHERE vehicle_type = 'E_BIKE'";

    public static double getScooterPrice() {
        return getVehiclePrice(GET_PRICE_FOR_SCOOTER);
    }

    public static double getCarPrice() {
        return getVehiclePrice(GET_PRICE_FOR_CAR);
    }

    public static double getBikePrice() {
        return getVehiclePrice(GET_PRICE_FOR_BIKE);
    }

    private static double getVehiclePrice(String query) {
        double price = 0.0;
        Connection connection = null;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement stmt = connection.prepareStatement(query);
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
