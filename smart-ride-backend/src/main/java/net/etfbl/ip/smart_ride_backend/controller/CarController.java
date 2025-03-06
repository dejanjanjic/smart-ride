package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.CarDetailedDTO;
import net.etfbl.ip.smart_ride_backend.dto.CarSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Car;
import net.etfbl.ip.smart_ride_backend.service.CarService;
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
@RequestMapping("api/v1/car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarSimpleDTO>> getAll(){
        return ResponseEntity.ok(this.carService.findAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<CarDetailedDTO> getById(@PathVariable String id){
        Car temp = carService.findById(id);
        if(temp == null){
            return ResponseEntity.notFound().build();
        }
        CarDetailedDTO carDetailedDTO = new CarDetailedDTO(temp);
        return ResponseEntity.ok(carDetailedDTO);
    }
    @GetMapping("{id}/image")
    public ResponseEntity<Resource> getImageById(@PathVariable("id")String id){
        try {
            Car car = carService.findById(id);
            if (car == null || car.getPicturePath() == null) {
                return ResponseEntity.notFound().build();
            }

            Path imagePath = Paths.get(car.getPicturePath());
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
    public ResponseEntity<Car> save(@RequestBody CarSimpleDTO car){
        Car temp = this.carService.save(car);
        return temp == null ? ResponseEntity.status(409).build() : ResponseEntity.ok(temp);
    }
//    @PutMapping()
//    public ResponseEntity<Car> update(@RequestBody Car car) {
//        Car temp = this.carService.update(car);
//        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
//    }
    @PutMapping("{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try{
            Car car = carService.saveImage(id, file);
            return car != null ? ResponseEntity.status(204).build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        boolean isDeleted = this.carService.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
