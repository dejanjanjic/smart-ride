package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.EScooterSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.EScooter;
import net.etfbl.ip.smart_ride_backend.service.EScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/e-scooter")
public class EScooterController {
    private final EScooterService eScooterService;

    @Autowired
    public EScooterController(EScooterService eScooterService) {
        this.eScooterService = eScooterService;
    }

    @GetMapping
    public ResponseEntity<List<EScooterSimpleDTO>> getAll() {
        return ResponseEntity.ok(this.eScooterService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<EScooter> getById(@PathVariable String id) {
        EScooter temp = eScooterService.findById(id);
        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
    }

    @PostMapping
    public ResponseEntity<EScooter> save(@RequestBody EScooterSimpleDTO eScooter) {
        EScooter temp = this.eScooterService.save(eScooter);
        return temp == null ? ResponseEntity.status(409).build() : ResponseEntity.ok(temp);
    }

//    @PutMapping
//    public ResponseEntity<EScooter> update(@RequestBody EScooter eScooter) {
//        EScooter temp = this.eScooterService.update(eScooter);
//        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        boolean isDeleted = this.eScooterService.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
