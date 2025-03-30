package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RentalPriceConfig {
    @Id
    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;
    BigDecimal price;

}
