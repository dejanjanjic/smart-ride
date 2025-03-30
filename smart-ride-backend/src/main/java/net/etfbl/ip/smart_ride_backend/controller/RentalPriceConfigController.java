package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.UpdateRentalPriceDTO;
import net.etfbl.ip.smart_ride_backend.model.RentalPriceConfig;
import net.etfbl.ip.smart_ride_backend.service.RentalPriceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rental-price-config")
public class RentalPriceConfigController {
    private final RentalPriceConfigService rentalPriceConfigService;

    @Autowired
    public RentalPriceConfigController(RentalPriceConfigService rentalPriceConfigService){
        this.rentalPriceConfigService = rentalPriceConfigService;
    }

    @GetMapping
    public ResponseEntity<UpdateRentalPriceDTO> getAll(){
        return ResponseEntity.ok(this.rentalPriceConfigService.getAll());
    }

    @PutMapping
    public ResponseEntity<List<RentalPriceConfig>> update(@RequestBody UpdateRentalPriceDTO updateRentalPriceDTO){
        System.out.println("Received DTO: " + updateRentalPriceDTO);
        return ResponseEntity.ok(rentalPriceConfigService.update(updateRentalPriceDTO));
    }
}
