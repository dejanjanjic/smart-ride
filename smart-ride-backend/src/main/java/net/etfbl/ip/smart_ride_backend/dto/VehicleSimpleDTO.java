package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.VehicleState;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleSimpleDTO {
    protected String id;
    protected String manufacturer;
    protected String model;
    protected BigDecimal purchasePrice;
    protected Double positionX;
    protected Double positionY;
    protected VehicleState vehicleState;
}
