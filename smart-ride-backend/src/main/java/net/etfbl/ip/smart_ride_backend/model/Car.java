package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.dto.CarSimpleDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Car extends Vehicle{
    private LocalDateTime purchaseDateTime;
    private String description;

    public Car(String id, Manufacturer manufacturer, String model, BigDecimal purchasePrice, String picturePath, LocalDateTime purchaseDateTime, String description) {
        super(id, manufacturer, model, purchasePrice, picturePath);
        this.purchaseDateTime = purchaseDateTime;
        this.description = description;
    }

}
