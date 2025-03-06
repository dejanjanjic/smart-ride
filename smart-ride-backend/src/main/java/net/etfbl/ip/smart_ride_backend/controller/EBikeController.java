package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.EBikeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.EBike;
import net.etfbl.ip.smart_ride_backend.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/e-bike")
public class EBikeController {
    private final EBikeService eBikeService;

    @Autowired
    public EBikeController(EBikeService eBikeService) {
        this.eBikeService = eBikeService;
    }

    @GetMapping
    public ResponseEntity<List<EBikeSimpleDTO>> getAll() {
        return ResponseEntity.ok(this.eBikeService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<EBike> getById(@PathVariable String id) {
        EBike temp = eBikeService.findById(id);
        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
    }

    @PostMapping
    public ResponseEntity<EBike> save(@RequestBody EBikeSimpleDTO eBike) {
        EBike temp = this.eBikeService.save(eBike);
        return temp == null ? ResponseEntity.status(409).build() : ResponseEntity.ok(temp);
    }

//    @PutMapping
//    public ResponseEntity<EBike> update(@RequestBody EBike eBike) {
//        EBike temp = this.eBikeService.update(eBike);
//        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        boolean isDeleted = this.eBikeService.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
