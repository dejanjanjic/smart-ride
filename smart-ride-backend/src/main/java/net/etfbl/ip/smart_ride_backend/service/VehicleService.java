package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.Failure;
import net.etfbl.ip.smart_ride_backend.model.Rental;
import net.etfbl.ip.smart_ride_backend.model.Vehicle;
import net.etfbl.ip.smart_ride_backend.model.VehicleState;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    public void declareVehicleState(Vehicle vehicle){
        if(vehicle != null){
            if(vehicle.getRentals().stream().anyMatch(Rental::getActive)){
                vehicle.setVehicleState(VehicleState.RENTED);
            } else if (vehicle.getFailures().stream().anyMatch(Failure::getActive)) {
                vehicle.setVehicleState(VehicleState.BROKEN);
            } else{
                vehicle.setVehicleState(VehicleState.AVAILABLE);
            }
        }
    }
}
