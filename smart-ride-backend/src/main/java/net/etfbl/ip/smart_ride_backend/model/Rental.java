package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;

    private Double startLocationX;
    private Double startLocationY;
    private Double endLocationX;
    private Double endLocationY;
    private Integer durationInSeconds;
    private BigDecimal price;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Rental(LocalDateTime dateTime, Double startLocationX, Double startLocationY, Double endLocationX, Double endLocationY, Integer durationInSeconds, BigDecimal price, Boolean active, Client client, Vehicle vehicle) {
        this.dateTime = dateTime;
        this.startLocationX = startLocationX;
        this.startLocationY = startLocationY;
        this.endLocationX = endLocationX;
        this.endLocationY = endLocationY;
        this.durationInSeconds = durationInSeconds;
        this.price = price;
        this.active = active;
        this.client = client;
        this.vehicle = vehicle;
    }
}
