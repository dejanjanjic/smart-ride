package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.FailureSimpleDTO;
import net.etfbl.ip.smart_ride_backend.dto.VerticalBarDataDTO;
import net.etfbl.ip.smart_ride_backend.service.FailureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/failure")
public class FailureController {
    private final FailureService failureService;

    @Autowired
    public  FailureController(FailureService failureService){
        this.failureService = failureService;
    }

    @GetMapping("vehicle/{id}")
    public ResponseEntity<List<FailureSimpleDTO>> getAllByVehicleId(@PathVariable("id") String id){
        return ResponseEntity.ok(failureService.findByVehicleId(id));
    }

    @GetMapping("by-vehicle")
    public ResponseEntity<List<VerticalBarDataDTO>> getFailuresByVehicle(){
        return ResponseEntity.ok(failureService.getFailuresByVehicle());
    }

    @PostMapping
    public ResponseEntity<FailureSimpleDTO> add(@RequestBody FailureSimpleDTO failureSimpleDTO){
        return ResponseEntity.ok(failureService.add(failureSimpleDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id")Long id){
        boolean deleted = this.failureService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
