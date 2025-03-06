package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.EScooter;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EScooterSimpleDTO extends VehicleSimpleDTO{
    private Integer maxSpeed;

    public EScooterSimpleDTO(EScooter eScooter){
        this.id = eScooter.getId();
        this.model = eScooter.getModel();
        this.manufacturer = eScooter.getManufacturer().getName();
        this.purchasePrice = eScooter.getPurchasePrice();
        this.maxSpeed = eScooter.getMaxSpeed();
    }
}
