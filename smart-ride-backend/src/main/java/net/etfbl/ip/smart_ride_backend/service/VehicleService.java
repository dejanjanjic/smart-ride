package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.Failure;
import net.etfbl.ip.smart_ride_backend.model.Rental;
import net.etfbl.ip.smart_ride_backend.model.Vehicle;
import net.etfbl.ip.smart_ride_backend.model.VehicleState;
import net.etfbl.ip.smart_ride_backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    public List<String> findAllIds() {
        return vehicleRepository
                .findAll()
                .stream()
                .map(Vehicle::getId)
                .toList();
    }
}
