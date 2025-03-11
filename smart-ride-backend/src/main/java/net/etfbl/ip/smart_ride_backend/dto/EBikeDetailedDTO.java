package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.EBike;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EBikeDetailedDTO extends VehicleDetailedDTO{
    private Integer maxRange;

    public EBikeDetailedDTO(EBike eBike){
        this.id = eBike.getId();
        this.manufacturer = eBike.getManufacturer().getName();
        this.model = eBike.getModel();
        this.purchasePrice = eBike.getPurchasePrice();
        this.maxRange = eBike.getMaxRange();
        this.rentals = eBike.getRentals();
        this.failures = eBike.getFailures();
        this.vehicleState = eBike.getVehicleState().name();
    }
}
