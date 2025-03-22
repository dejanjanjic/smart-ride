package net.etfbl.ip.smart_ride_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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
    @OneToMany(mappedBy = "vehicle")
    @JsonBackReference
    private List<Failure> failures = new ArrayList<>();
    @OneToMany(mappedBy = "vehicle")
    @JsonBackReference
    private List<Rental> rentals = new ArrayList<>();
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