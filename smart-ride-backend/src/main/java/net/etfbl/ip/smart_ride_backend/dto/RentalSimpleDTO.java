package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Rental;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalSimpleDTO {
    private Long id;
    private LocalDateTime dateTime;
    private Integer durationInSeconds;
    private String clientName;
    private String vehicleId;

    public RentalSimpleDTO(Rental rental){
        this.id = rental.getId();
        this.dateTime = rental.getDateTime();
        this.durationInSeconds = rental.getDurationInSeconds();
        this.clientName = rental.getClient().getFirstName() + " " + rental.getClient().getLastName();
        this.vehicleId = rental.getVehicle().getId();
    }
}
