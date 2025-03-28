package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Failure;
import net.etfbl.ip.smart_ride_backend.model.Rental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDetailedDTO {
    protected String id;
    protected String manufacturer;
    protected String model;
    protected BigDecimal purchasePrice;
    protected String vehicleState;
}
