package net.etfbl.ip.smart_ride_backend.controller;


import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
import net.etfbl.ip.smart_ride_backend.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manufacturer")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService){
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public ResponseEntity<List<Manufacturer>> getAll(){
        return ResponseEntity.ok(this.manufacturerService.findAll());
    }
    @GetMapping("names")
    public ResponseEntity<List<String>> getAllNames(){
        return ResponseEntity.ok(this.manufacturerService.findAllNames());
    }

    @GetMapping("search/{keyword}") ResponseEntity<List<Manufacturer>> getAllByManufacturerNameOrModel(@PathVariable String keyword){
        return ResponseEntity.ok(this.manufacturerService.findByName(keyword));
    }

    @GetMapping("{id}")
    public ResponseEntity<Manufacturer> getById(@PathVariable Long id){
        Manufacturer temp = manufacturerService.findById(id);
        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
    }
    @PostMapping
    public ResponseEntity<Manufacturer> save(@RequestBody Manufacturer manufacturer){
        Manufacturer temp = this.manufacturerService.save(manufacturer);
        if(temp == null){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.ok(temp);
    }
    @PutMapping()
    public ResponseEntity<Manufacturer> update(@RequestBody Manufacturer manufacturer) {
        Manufacturer temp = this.manufacturerService.update(manufacturer);
        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean isDeleted = this.manufacturerService.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
