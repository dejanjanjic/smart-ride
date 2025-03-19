package net.etfbl.ip.smart_ride_backend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Failure;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailureSimpleDTO {
    private Long id;
    private String description;
    private LocalDateTime dateTime;

    public FailureSimpleDTO(Failure failure){
        this.id = failure.getId();
        this.description = failure.getDescription();
        this.dateTime = failure.getDateTime();
    }
}
