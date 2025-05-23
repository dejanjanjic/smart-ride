package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.RentalDTO;
import net.etfbl.ip.smart_ride_backend.dto.RentalSimpleDTO;
import net.etfbl.ip.smart_ride_backend.dto.StartEndDateTimeDTO;
import net.etfbl.ip.smart_ride_backend.dto.VerticalBarDataDTO;
import net.etfbl.ip.smart_ride_backend.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rental")
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

    @GetMapping("revenue-by-vehicle-type")
    ResponseEntity<List<VerticalBarDataDTO>> getRevenueByVehicleType(){
        return ResponseEntity.ok(rentalService.getTotalRevenueByVehicleType());
    }
    @PostMapping("revenue-by-day")
    ResponseEntity<List<VerticalBarDataDTO>> getRevenueByDay(@RequestBody StartEndDateTimeDTO startEndDateTimeDTO){
        return ResponseEntity.ok(rentalService.getRevenueByDay(startEndDateTimeDTO));
    }

    @PostMapping
    ResponseEntity<RentalDTO> add(@RequestBody RentalDTO rentalDTO){
        RentalDTO temp = rentalService.add(rentalDTO);
        return ResponseEntity.ok(temp);
    }
}
