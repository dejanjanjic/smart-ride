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
public class EBike extends Vehicle {
    private Integer maxRange;

    public EBike(String id, Manufacturer manufacturer, String model, BigDecimal purchasePrice, double positionX, double positionY, String picturePath, VehicleState vehicleState, Integer maxRange) {
        super(id, manufacturer, model, purchasePrice, positionX, positionY, picturePath, vehicleState);
        this.maxRange = maxRange;
    }
}
