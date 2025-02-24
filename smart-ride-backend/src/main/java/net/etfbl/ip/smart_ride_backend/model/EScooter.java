package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class EScooter extends Vehicle{
    private Integer maxSpeed;

    public EScooter(String id, Manufacturer manufacturer, String model, BigDecimal purchasePrice, String picturePath, Integer maxSpeed) {
        super(id, manufacturer, model, purchasePrice, picturePath);
        this.maxSpeed = maxSpeed;
    }
}
