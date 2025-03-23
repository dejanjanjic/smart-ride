package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.EBike;
import net.etfbl.ip.smart_ride_backend.model.EScooter;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EScooterDetailedDTO extends VehicleDetailedDTO{
    private Integer maxSpeed;

    public EScooterDetailedDTO(EScooter eScooter){
        this.id = eScooter.getId();
        this.manufacturer = eScooter.getManufacturer().getName();
        this.model = eScooter.getModel();
        this.purchasePrice = eScooter.getPurchasePrice();
        this.maxSpeed = eScooter.getMaxSpeed();
        this.vehicleState = eScooter.getVehicleState().name();
    }
}
