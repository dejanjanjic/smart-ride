package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.model.Car;
import net.etfbl.ip.smart_ride_backend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Car>> getAll(){
        return ResponseEntity.ok(this.carService.findAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<Car> getById(@PathVariable String id){
        Car temp = carService.findById(id);
        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
    }
    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car car){
        Car temp = this.carService.save(car);
        return temp == null ? ResponseEntity.status(409).build() : ResponseEntity.ok(temp);
    }
    @PutMapping()
    public ResponseEntity<Car> update(@RequestBody Car car) {
        Car temp = this.carService.update(car);
        return temp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(temp);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        boolean isDeleted = this.carService.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
