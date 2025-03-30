package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.etfbl.ip.smart_ride_backend.model.RentalPriceConfig;
import net.etfbl.ip.smart_ride_backend.model.VehicleType;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UpdateRentalPriceDTO {
    private BigDecimal carPrice;
    private BigDecimal bikePrice;
    private BigDecimal scooterPrice;

    public UpdateRentalPriceDTO(List<RentalPriceConfig> rentalPriceConfigs){
        for(RentalPriceConfig r : rentalPriceConfigs){
            if(r.getVehicleType().equals(VehicleType.CAR)){
                this.carPrice = r.getPrice();
            } else if (r.getVehicleType().equals(VehicleType.E_BIKE)) {
                this.bikePrice = r.getPrice();
            } else if (r.getVehicleType().equals(VehicleType.E_SCOOTER)){
                this.scooterPrice = r.getPrice();
            }
        }
    }
}
