package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.RentalSimpleDTO;
import net.etfbl.ip.smart_ride_backend.repository.RentalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository){
        this.rentalRepository = rentalRepository;
    }
    public List<RentalSimpleDTO> findAll() {
        return rentalRepository
                .findAll()
                .stream()
                .map(RentalSimpleDTO::new)
                .toList();
    }

    public List<RentalSimpleDTO> findAllByVehicleId(String id) {
        return rentalRepository
                .findAllByVehicle_Id(id)
                .stream()
                .map(RentalSimpleDTO::new)
                .toList();
    }
}
