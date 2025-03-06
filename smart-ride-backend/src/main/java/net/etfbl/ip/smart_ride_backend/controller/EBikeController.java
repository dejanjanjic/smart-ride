package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.EBikeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.EBike;
import net.etfbl.ip.smart_ride_backend.service.EBikeService;
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

    @GetMapping("{id}/image")
    public ResponseEntity<Resource> getImageById(@PathVariable("id")String id){
        try {
            EBike eBike = eBikeService.findById(id);
            if (eBike == null || eBike.getPicturePath() == null) {
                return ResponseEntity.notFound().build();
            }

            Path imagePath = Paths.get(eBike.getPicturePath());
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
    public ResponseEntity<EBike> save(@RequestBody EBikeSimpleDTO eBike) {
        EBike temp = this.eBikeService.save(eBike);
        return temp == null ? ResponseEntity.status(409).build() : ResponseEntity.ok(temp);
    }

//    @PutMapping
//    public ResponseEntity<EBike> update(@RequestBody EBike eBike) {
//        EBike temp = this.eBikeService.update(eBike);
//        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
//    }

    @PutMapping("{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try{
            EBike eBike = eBikeService.saveImage(id, file);
            return eBike != null ? ResponseEntity.status(204).build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        boolean isDeleted = this.eBikeService.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
