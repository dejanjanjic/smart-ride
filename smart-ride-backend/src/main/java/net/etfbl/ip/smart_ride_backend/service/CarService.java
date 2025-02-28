package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.CarSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Car;
import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
import net.etfbl.ip.smart_ride_backend.repository.CarRepository;
import net.etfbl.ip.smart_ride_backend.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public CarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository){
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<CarSimpleDTO> findAll() {
        List<CarSimpleDTO> temp = new ArrayList<>();
        for (Car car : this.carRepository.findAll()){
            temp.add(new CarSimpleDTO(car));
        }
        return temp;
    }

    public Car findById(String id) {
        return this.carRepository.findById(id).orElse(null);
    }

    public Car save(CarSimpleDTO car) {
        boolean carExist = carRepository.existsById(car.getId());
        Manufacturer manufacturer = manufacturerRepository.findByName(car.getManufacturer());
        if(carExist || manufacturer == null){
            return null;
        }
        Car newCar = new Car(car.getId(), manufacturer, car.getModel(), car.getPurchasePrice(), null, car.getPurchaseDateTime(), car.getDescription());
        return carRepository.save(newCar);
    }


//    public Car update(Car car) {
//        Car temp = carRepository.findById(car.getId()).orElse(null);
//        if(temp == null){
//            return null;
//        }
//        if(car.getModel() != null){
//            temp.setModel(car.getModel());
//        }
//        if(car.getManufacturer() != null){
//            temp.setManufacturer(car.getManufacturer());
//        }
//        if(car.getFailures() != null){
//            temp.setFailures(car.getFailures());
//        }
//        if(car.getRentals() != null){
//            temp.setRentals(car.getRentals());
//        }
//        if(car.getPicturePath() != null){
//            temp.setPicturePath(car.getPicturePath());
//        }
//        if(car.getPurchasePrice() != null){
//            temp.setPurchasePrice(car.getPurchasePrice());
//        }
//        if(car.getDescription() != null){
//            temp.setDescription(car.getDescription());
//        }
//        if(car.getPurchaseDateTime() != null){
//            temp.setPurchaseDateTime(car.getPurchaseDateTime());
//        }
//        return carRepository.save(temp);
//    }

    public boolean deleteById(String id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
