package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.EBikeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.dto.EScooterSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.*;
import net.etfbl.ip.smart_ride_backend.repository.EScooterRepository;
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
import java.util.List;
import java.util.Objects;

@Service
public class EScooterService {
    private final EScooterRepository eScooterRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final RentalRepository rentalRepository;
    private final FailureRepository failureRepository;

    @Value("${images.e-scooter}")
    private String uploadDir;

    @Autowired
    public EScooterService(EScooterRepository eScooterRepository, ManufacturerRepository manufacturerRepository, RentalRepository rentalRepository, FailureRepository failureRepository) {
        this.eScooterRepository = eScooterRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.rentalRepository = rentalRepository;
        this.failureRepository = failureRepository;
    }

    public List<EScooterSimpleDTO> findAll() {
        return this.eScooterRepository.findAll().stream().map(EScooterSimpleDTO::new).toList();
    }

    public EScooter findById(String id) {
        EScooter eScooter = this.eScooterRepository.findById(id).orElse(null);
        declareVehicleState(eScooter);
        return eScooter;
    }

    public List<EScooterSimpleDTO> findByManufacturerNameOrModel(String keyword) {
        return eScooterRepository.searchByKeyword(keyword)
                .stream()
                .map(EScooterSimpleDTO::new)
                .toList();
    }

    public EScooter save(EScooterSimpleDTO eScooter) {
        boolean exist = eScooterRepository.existsById(eScooter.getId());
        Manufacturer manufacturer = manufacturerRepository.findByName(eScooter.getManufacturer());
        if (exist || manufacturer == null) {
            return null;
        }
        Double posX = RandomGenerator.getRandomPosition(44.75548087994783, 44.79654652278502);
        Double posY = RandomGenerator.getRandomPosition(17.170851491908504, 17.229302191354293);
        EScooter temp = new EScooter(eScooter.getId(), manufacturer, eScooter.getModel(), eScooter.getPurchasePrice(), posX, posY, null, eScooter.getVehicleState(), eScooter.getMaxSpeed());
        return eScooterRepository.save(temp);
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
//    public EScooter update(EScooter eScooter) {
//        EScooter temp = eScooterRepository.findById(eScooter.getId()).orElse(null);
//        if (temp == null) {
//            return null;
//        }
//        if (eScooter.getModel() != null) {
//            temp.setModel(eScooter.getModel());
//        }
//        if (eScooter.getManufacturer() != null) {
//            temp.setManufacturer(eScooter.getManufacturer());
//        }
//        if (eScooter.getFailures() != null) {
//            temp.setFailures(eScooter.getFailures());
//        }
//        if (eScooter.getRentals() != null) {
//            temp.setRentals(eScooter.getRentals());
//        }
//        if (eScooter.getPicturePath() != null) {
//            temp.setPicturePath(eScooter.getPicturePath());
//        }
//        if (eScooter.getPurchasePrice() != null) {
//            temp.setPurchasePrice(eScooter.getPurchasePrice());
//        }
//        if (eScooter.getMaxSpeed() != null) {
//            temp.setMaxSpeed(eScooter.getMaxSpeed());
//        }
//        return eScooterRepository.save(temp);
//    }


    public EScooter saveImage(String id, MultipartFile file) throws IOException {
        EScooter eScooter = eScooterRepository.findById(id).orElse(null);
        if(eScooter == null) return null;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        Path filePath = uploadPath.resolve(id + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")));
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        eScooter.setPicturePath(filePath.toAbsolutePath().toString());
        eScooterRepository.save(eScooter);

        return eScooter;
    }

    public boolean deleteById(String id) {
        if (eScooterRepository.existsById(id)) {
            eScooterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}