package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class EScooter extends Vehicle{
    private Integer maxSpeed;

    public EScooter(String id, Manufacturer manufacturer, String model, BigDecimal purchasePrice, double positionX, double positionY, String picturePath, VehicleState vehicleState, Integer maxSpeed) {
        super(id, manufacturer, model, purchasePrice, positionX, positionY, picturePath, vehicleState);
        this.maxSpeed = maxSpeed;
    }
}
