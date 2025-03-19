package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.RentalSimpleDTO;
import net.etfbl.ip.smart_ride_backend.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/rental/")
public class RentalController {
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalSimpleDTO>> getAll(){
        return ResponseEntity.ok(rentalService.findAll());
    }

    @GetMapping("vehicle/{id}")
    ResponseEntity<List<RentalSimpleDTO>> getAllByVehicleId(@PathVariable("id") String id){
        return ResponseEntity.ok(rentalService.findAllByVehicleId(id));
    }
}
