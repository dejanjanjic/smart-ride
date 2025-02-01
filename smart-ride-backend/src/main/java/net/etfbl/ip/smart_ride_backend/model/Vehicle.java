package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Vehicle {
    @Id
    private String id;
    private String manufacturer;
    private String model;
    private Double purchasePrice;

}