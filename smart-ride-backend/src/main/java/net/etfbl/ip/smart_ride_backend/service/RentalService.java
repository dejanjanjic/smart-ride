package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.RentalDTO;
import net.etfbl.ip.smart_ride_backend.dto.RentalSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Client;
import net.etfbl.ip.smart_ride_backend.model.Rental;
import net.etfbl.ip.smart_ride_backend.model.Vehicle;
import net.etfbl.ip.smart_ride_backend.repository.ClientRepository;
import net.etfbl.ip.smart_ride_backend.repository.RentalRepository;
import net.etfbl.ip.smart_ride_backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, ClientRepository clientRepository, VehicleRepository vehicleRepository){
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
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

    public RentalDTO add(RentalDTO rentalDTO) {
        Client client = clientRepository.findById(rentalDTO.getClientId()).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(rentalDTO.getVehicleId()).orElse(null);
        if(client == null || vehicle == null){
            return null;
        }
        Rental rental = new Rental(
                rentalDTO.getDateTime(),
                rentalDTO.getStartLocationX(),
                rentalDTO.getStartLocationY(),
                rentalDTO.getEndLocationX(),
                rentalDTO.getEndLocationY(),
                rentalDTO.getDurationInSeconds(),
                rentalDTO.getPrice(),
                rentalDTO.getActive(),
                client,
                vehicle);
        return new RentalDTO(rentalRepository.save(rental));

    }
}
