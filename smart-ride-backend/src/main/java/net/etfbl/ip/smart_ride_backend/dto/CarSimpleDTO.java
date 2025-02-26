package net.etfbl.ip.smart_ride_backend.dto;

import lombok.Data;
import net.etfbl.ip.smart_ride_backend.model.Car;

import java.time.LocalDateTime;

@Data
public class CarSimpleDTO extends VehicleSimpleDTO{
    private LocalDateTime purchaseDateTime;

    public CarSimpleDTO(Car car){
        this.id = car.getId();
        this.manufacturer = car.getManufacturer().getName();
        this.model = car.getModel();
        this.purchasePrice = car.getPurchasePrice();
        this.purchaseDateTime = car.getPurchaseDateTime();
    }
}
