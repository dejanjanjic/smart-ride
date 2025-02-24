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

    public EBike(String id, Manufacturer manufacturer, String model, BigDecimal purchasePrice, String picturePath, Integer maxRange) {
        super(id, manufacturer, model, purchasePrice, picturePath);
        this.maxRange = maxRange;
    }
}
