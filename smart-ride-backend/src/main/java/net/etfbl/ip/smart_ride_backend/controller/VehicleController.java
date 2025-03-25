package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.model.Vehicle;
import net.etfbl.ip.smart_ride_backend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll(){
        return ResponseEntity.ok(vehicleService.findAll());
    }
    @GetMapping("ids")
    public ResponseEntity<List<String>> getVehicleIds(){
        return ResponseEntity.ok(vehicleService.findAllIds());
    }
}
