package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car extends Vehicle{
    private LocalDateTime purchaseDateTime;
    private String description;

}
