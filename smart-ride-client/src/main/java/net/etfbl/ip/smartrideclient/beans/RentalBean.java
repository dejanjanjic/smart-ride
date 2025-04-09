package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.RentalDAO;
import net.etfbl.ip.smartrideclient.dao.RentalPriceConfigDAO;
import net.etfbl.ip.smartrideclient.dao.ScooterDAO;
import net.etfbl.ip.smartrideclient.dao.VehicleDAO;
import net.etfbl.ip.smartrideclient.dto.Location;
import net.etfbl.ip.smartrideclient.dto.Rental;
import net.etfbl.ip.smartrideclient.dto.Scooter;

import java.math.BigDecimal; // Importuj BigDecimal
import java.time.Duration;
import java.time.LocalDateTime;

public class RentalBean {
    // Promeni povratni tip u Integer (ili Long)
    public Long startRental(Long userId, String vehicleId) {
        LocalDateTime startTime = LocalDateTime.now();
        boolean active = true;
        Long rentalId = null;

        try {
            Location startLocation = VehicleDAO.getLocation(vehicleId);
            if (startLocation == null) {
                System.err.println("RentalBean: Could not find start location for vehicle " + vehicleId);
                return null;
            }

            Rental newRental = new Rental(active, startTime,0, vehicleId, userId, BigDecimal.ZERO,
                    startLocation.getPositionX(), startLocation.getPositionY());

            rentalId = RentalDAO.addRental(newRental);

            if (rentalId != null) {
                System.out.println("RentalBean: Successfully created rental with ID: " + rentalId + " for user " + userId + ", vehicle " + vehicleId);
            } else {
                System.err.println("RentalBean: RentalDAO.addRental returned null (failed) for user " + userId + ", vehicle " + vehicleId);
            }
            return rentalId;

        } catch (Exception e) {
            System.err.println("RentalBean: Exception occurred while trying to start rental for user " + userId + ", vehicle " + vehicleId);
            e.printStackTrace();
            return null;
        }
    }

    public Rental finishScooterRentalById(Long rentalId, Long userId) {
        Rental rental = RentalDAO.findActiveRentalByIdAndUserId(rentalId, userId);
        if(rental != null){
            int durationInSeconds = ((int) Duration.between(rental.getDateTime(), LocalDateTime.now()).getSeconds());
            rental.setDurationInSeconds(durationInSeconds);
            double pricePerSecond = RentalPriceConfigDAO.getScooterPrice();
            BigDecimal price = BigDecimal.valueOf(pricePerSecond * durationInSeconds);
            rental.setPrice(price);
            double locationX = getRandomLocationX();
            double locationY = getRandomLocationY();
            rental.setEndLocationX(locationX);
            rental.setEndLocationY(locationY);

            ScooterDAO.updateLocation(rental.getVehicleId(), locationX, locationY);
            RentalDAO.updateRentalEndDetails(rentalId, durationInSeconds, price, locationX, locationY);
            return rental;
        }
        return null;
    }



    private double getRandomLocationX() {
        return 44.75548087994783 + Math.random() * (44.79654652278502 - 44.75548087994783);
    }

    private double getRandomLocationY() {
        return 17.170851491908504 + Math.random() * (17.229302191354293 - 17.170851491908504);
    }
}