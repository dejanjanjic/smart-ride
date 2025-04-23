package net.etfbl.ip.smart_ride_backend.service;

import jakarta.transaction.Transactional;
import net.etfbl.ip.smart_ride_backend.dto.CarSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.*;
import net.etfbl.ip.smart_ride_backend.repository.CarRepository;
import net.etfbl.ip.smart_ride_backend.repository.FailureRepository;
import net.etfbl.ip.smart_ride_backend.repository.ManufacturerRepository;
import net.etfbl.ip.smart_ride_backend.repository.RentalRepository;
import net.etfbl.ip.smart_ride_backend.util.RandomGenerator;
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
public class CarService{
    private final CarRepository carRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final RentalRepository rentalRepository;
    private final FailureRepository failureRepository;

    @Value("${images.car}")
    private String uploadDir;

    @Autowired
    public CarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository, RentalRepository rentalRepository, FailureRepository failureRepository){
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.rentalRepository = rentalRepository;
        this.failureRepository = failureRepository;
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
        Double posX = RandomGenerator.getRandomPosition(44.75548087994783, 44.79654652278502);
        Double posY = RandomGenerator.getRandomPosition(17.170851491908504, 17.229302191354293);
        Car newCar = new Car(car.getId(), manufacturer, car.getModel(), car.getPurchasePrice(), posX, posY, null, car.getVehicleState(), car.getPurchaseDateTime(), car.getDescription());
        return carRepository.save(newCar);
    }

    public void declareVehicleState(Vehicle vehicle){
        if(vehicle != null){
            if(rentalRepository.findAllByVehicle_Id(vehicle.getId()).stream().anyMatch(Rental::getActive)){
                vehicle.setVehicleState(VehicleState.RENTED);
            } else if (!failureRepository.findAllByVehicle_Id(vehicle.getId()).isEmpty()) {
                vehicle.setVehicleState(VehicleState.BROKEN);
            } else{
                vehicle.setVehicleState(VehicleState.AVAILABLE);
            }
        }
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

    @Transactional
    public boolean deleteById(String id) {
        if (carRepository.existsById(id)) {
            rentalRepository.deleteAllByVehicle_Id(id);
            failureRepository.deleteAllByVehicle_Id(id);
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

    public List<CarSimpleDTO> findByManufacturerNameOrModel(String keyword) {
        return carRepository.searchByKeyword(keyword)
                .stream()
                .map(CarSimpleDTO::new)
                .toList();
    }
}
