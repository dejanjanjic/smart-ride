package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.CarSimpleDTO;
import net.etfbl.ip.smart_ride_backend.dto.EScooterDetailedDTO;
import net.etfbl.ip.smart_ride_backend.dto.EScooterSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.EScooter;
import net.etfbl.ip.smart_ride_backend.service.EScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<EScooterDetailedDTO> getById(@PathVariable String id) {
        EScooter temp = eScooterService.findById(id);
        if (temp == null){
            return ResponseEntity.notFound().build();
        }
        EScooterDetailedDTO eScooterDetailedDTO = new EScooterDetailedDTO(temp);
        return ResponseEntity.ok(eScooterDetailedDTO);
    }

    @GetMapping("search/{keyword}") ResponseEntity<List<EScooterSimpleDTO>> getAllByManufacturerNameOrModel(@PathVariable String keyword){
        return ResponseEntity.ok(this.eScooterService.findByManufacturerNameOrModel(keyword));
    }

    @GetMapping("{id}/image")
    public ResponseEntity<Resource> getImageById(@PathVariable("id")String id){
        try {
            EScooter eScooter = eScooterService.findById(id);
            if (eScooter == null || eScooter.getPicturePath() == null) {
                return ResponseEntity.notFound().build();
            }

            Path imagePath = Paths.get(eScooter.getPicturePath());
            Resource resource = new UrlResource(imagePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }


            return ResponseEntity.ok()
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @PutMapping("{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try{
            EScooter eScooter = eScooterService.saveImage(id, file);
            return eScooter != null ? ResponseEntity.status(204).build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        boolean isDeleted = this.eScooterService.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
