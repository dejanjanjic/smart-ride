package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Car;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailedDTO extends VehicleDetailedDTO{
    private LocalDateTime purchaseDateTime;
    private String description;

    public CarDetailedDTO(Car car){
        this.id = car.getId();
        this.manufacturer = car.getManufacturer().getName();
        this.model = car.getModel();
        this.purchasePrice = car.getPurchasePrice();
        this.purchaseDateTime = car.getPurchaseDateTime();
        this.description = car.getDescription();
        this.vehicleState = car.getVehicleState().name();
    }
}