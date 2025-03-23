package net.etfbl.ip.smart_ride_backend.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
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
    protected String picturePath;
    @Transient
    @Enumerated(EnumType.STRING)
    private VehicleState vehicleState;

    public Vehicle(String id, Manufacturer manufacturer, String model, BigDecimal purchasePrice, String picturePath) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.picturePath = picturePath;
    }
}