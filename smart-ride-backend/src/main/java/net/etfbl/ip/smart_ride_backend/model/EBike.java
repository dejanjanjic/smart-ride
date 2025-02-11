package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class EBike extends Vehicle {
    private int maxRange;

    public EBike(String id, String manufacturer, String model, BigDecimal purchasePrice, String picturePath, int maxRange) {
        super(id, manufacturer, model, purchasePrice, picturePath);
        this.maxRange = maxRange;
    }
}
