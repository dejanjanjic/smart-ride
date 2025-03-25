package net.etfbl.ip.smart_ride_backend.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(
        strategy = InheritanceType.JOINED
)
public abstract class Vehicle {
    @Id
    protected String id;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    protected Manufacturer manufacturer;
    protected String model;
    protected BigDecimal purchasePrice;
    protected Double positionX;
    protected Double positionY;
    protected String picturePath;
    @Transient
    @Enumerated(EnumType.STRING)
    private VehicleState vehicleState;
}