package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.Car;
import net.etfbl.ip.smart_ride_backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<Car> findAll() {
        return this.carRepository.findAll();
    }

    public Car findById(String id) {
        return this.carRepository.findById(id).orElse(null);
    }

    public Car save(Car car) {
        boolean exist = carRepository.existsById(car.getId());
        if(exist){
            return null;
        }
        return carRepository.save(car);
    }


    public Car update(Car car) {
        Car temp = carRepository.findById(car.getId()).orElse(null);
        if(temp == null){
            return null;
        }
        if(car.getModel() != null){
            temp.setModel(car.getModel());
        }
        if(car.getManufacturer() != null){
            temp.setManufacturer(car.getManufacturer());
        }
        if(car.getFailures() != null){
            temp.setFailures(car.getFailures());
        }
        if(car.getRentals() != null){
            temp.setRentals(car.getRentals());
        }
        if(car.getPicturePath() != null){
            temp.setPicturePath(car.getPicturePath());
        }
        if(car.getPurchasePrice() != null){
            temp.setPurchasePrice(car.getPurchasePrice());
        }
        if(car.getDescription() != null){
            temp.setDescription(car.getDescription());
        }
        if(car.getPurchaseDateTime() != null){
            temp.setPurchaseDateTime(car.getPurchaseDateTime());
        }
        return carRepository.save(temp);
    }

    public boolean deleteById(String id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
