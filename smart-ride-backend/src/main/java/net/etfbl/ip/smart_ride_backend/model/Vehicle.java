package net.etfbl.ip.smart_ride_backend.model;

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
    protected String manufacturer;
    protected String model;
    protected BigDecimal purchasePrice;
    protected String picturePath;
    @OneToMany(mappedBy = "vehicle")
    private List<Failure> failures = new ArrayList<>();
    @OneToMany(mappedBy = "vehicle")
    private List<Rental> rentals = new ArrayList<>();

    public Vehicle(String id, String manufacturer, String model, BigDecimal purchasePrice, String picturePath) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.picturePath = picturePath;
    }
}