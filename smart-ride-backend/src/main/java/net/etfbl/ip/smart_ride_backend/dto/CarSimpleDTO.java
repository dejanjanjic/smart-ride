package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Car;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarSimpleDTO extends VehicleSimpleDTO{
    private LocalDateTime purchaseDateTime;
    private String description;

    public CarSimpleDTO(Car car){
        this.id = car.getId();
        this.manufacturer = car.getManufacturer().getName();
        this.model = car.getModel();
        this.purchasePrice = car.getPurchasePrice();
        this.purchaseDateTime = car.getPurchaseDateTime();
        this.description = car.getDescription();

    }
}
