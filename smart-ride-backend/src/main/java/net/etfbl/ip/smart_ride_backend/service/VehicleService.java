package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.Failure;
import net.etfbl.ip.smart_ride_backend.model.Rental;
import net.etfbl.ip.smart_ride_backend.model.Vehicle;
import net.etfbl.ip.smart_ride_backend.model.VehicleState;
import net.etfbl.ip.smart_ride_backend.repository.FailureRepository;
import net.etfbl.ip.smart_ride_backend.repository.RentalRepository;
import net.etfbl.ip.smart_ride_backend.repository.VehicleRepository;
import net.etfbl.ip.smart_ride_backend.util.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final FailureRepository failureRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, RentalRepository rentalRepository, FailureRepository failureRepository){
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
        this.failureRepository = failureRepository;
    }

    public List<Vehicle> findAll(){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        for(Vehicle vehicle : vehicles){
            declareVehicleState(vehicle);
        }
        return vehicles;
    }
    public List<String> findAllIds() {
        return vehicleRepository
                .findAll()
                .stream()
                .map(Vehicle::getId)
                .toList();
    }

    public void declareVehicleState(Vehicle vehicle){
        if(vehicle != null){
            if(rentalRepository.findAllByVehicle_Id(vehicle.getId()).stream().anyMatch(Rental::getActive)){
                vehicle.setVehicleState(VehicleState.RENTED);
            } else if (!failureRepository.findAllByVehicle_Id(vehicle.getId()).isEmpty()) {
                vehicle.setVehicleState(VehicleState.BROKEN);
            } else{
                vehicle.setVehicleState(VehicleState.AVAILABLE);
            }
        }
    }
}
