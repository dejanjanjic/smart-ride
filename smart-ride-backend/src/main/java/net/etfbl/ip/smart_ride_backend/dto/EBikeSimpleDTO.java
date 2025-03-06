package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.dto.VehicleSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Car;
import net.etfbl.ip.smart_ride_backend.model.EBike;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EBikeSimpleDTO extends VehicleSimpleDTO {
    private Integer maxRange;

    public EBikeSimpleDTO(EBike eBike){
        this.id = eBike.getId();
        this.manufacturer = eBike.getManufacturer().getName();
        this.model = eBike.getModel();
        this.purchasePrice = eBike.getPurchasePrice();
        this.maxRange = eBike.getMaxRange();

    }
}