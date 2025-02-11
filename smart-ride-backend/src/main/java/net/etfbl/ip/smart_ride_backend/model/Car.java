package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Car extends Vehicle{
    private LocalDateTime purchaseDateTime;
    private String description;

    public Car(String id, String manufacturer, String model, BigDecimal purchasePrice, String picturePath, LocalDateTime purchaseDateTime, String description) {
        super(id, manufacturer, model, purchasePrice, picturePath);
        this.purchaseDateTime = purchaseDateTime;
        this.description = description;
    }
}
