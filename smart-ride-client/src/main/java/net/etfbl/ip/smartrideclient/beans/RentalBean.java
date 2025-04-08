package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.RentalDAO;
import net.etfbl.ip.smartrideclient.dao.VehicleDAO;
import net.etfbl.ip.smartrideclient.dto.Location;
import net.etfbl.ip.smartrideclient.dto.Rental;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RentalBean {
    public boolean startRental(Long userId, String vehicleId) {
        LocalDateTime startTime = LocalDateTime.now();
        boolean active = true;

        try {
            Location startLocation = VehicleDAO.getLocation(vehicleId);
            boolean success = RentalDAO.addRental(new Rental(active, startTime, 0, vehicleId, userId, null, startLocation.getPositionX(), startLocation.getPositionY()));

            if (success) {
                System.out.println("RentalBean: Successfully initiated call to create rental for user " + userId + ", vehicle " + vehicleId);
            } else {
                System.err.println("RentalBean: RentalDAO.createRental returned false for user " + userId + ", vehicle " + vehicleId);
            }
            return success;

        } catch (Exception e) {
            System.err.println("RentalBean: Exception occurred while calling RentalDAO.createRental for user " + userId + ", vehicle " + vehicleId);
            e.printStackTrace();
            return false;
        }
    }
}
