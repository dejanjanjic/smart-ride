package net.etfbl.ip.smart_ride_backend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Client;
import net.etfbl.ip.smart_ride_backend.model.Rental;
import net.etfbl.ip.smart_ride_backend.model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private Long id;
    private LocalDateTime dateTime;

    private Double startLocationX;
    private Double startLocationY;
    private Double endLocationX;
    private Double endLocationY;
    private Integer durationInSeconds;
    private BigDecimal price;
    private Boolean active;
    private Long clientId;
    private String vehicleId;

    public RentalDTO(Rental rental) {
        this.id = rental.getId();
        this.dateTime = rental.getDateTime();
        this.startLocationX = rental.getStartLocationX();
        this.startLocationY = rental.getStartLocationY();
        this.endLocationX = rental.getEndLocationX();
        this.endLocationY = rental.getEndLocationY();
        this.durationInSeconds = rental.getDurationInSeconds();
        this.price = rental.getPrice();
        this.active = rental.getActive();
        this.clientId = rental.getClient().getId();
        this.vehicleId = rental.getVehicle().getId();
    }
}
