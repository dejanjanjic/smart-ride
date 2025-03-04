package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.CarSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.*;
import net.etfbl.ip.smart_ride_backend.repository.CarRepository;
import net.etfbl.ip.smart_ride_backend.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CarService extends VehicleService{
    private final CarRepository carRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Value("${images.car}")
    private String uploadDir;

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
        Car car = this.carRepository.findById(id).orElse(null);
        declareVehicleState(car);
        return car;
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

    public Car saveImage(String id, MultipartFile file) throws IOException {
        Car car = carRepository.findById(id).orElse(null);
        if(car == null) return null;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        Path filePath = uploadPath.resolve(id + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")));
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        car.setPicturePath(filePath.toAbsolutePath().toString());
        carRepository.save(car);

        return car;
    }
}
