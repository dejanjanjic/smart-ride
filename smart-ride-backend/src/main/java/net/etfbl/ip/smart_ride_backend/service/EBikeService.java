package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.CarSimpleDTO;
import net.etfbl.ip.smart_ride_backend.dto.EBikeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.EBike;
import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
import net.etfbl.ip.smart_ride_backend.repository.EBikeRepository;
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
import java.util.List;
import java.util.Objects;

@Service
public class EBikeService extends VehicleService{
    private final EBikeRepository eBikeRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Value("${images.e-bike}")
    private String uploadDir;

    @Autowired
    public EBikeService(EBikeRepository eBikeRepository, ManufacturerRepository manufacturerRepository) {
        this.eBikeRepository = eBikeRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<EBikeSimpleDTO> findAll() {
        return this.eBikeRepository.findAll().stream()
                .map(EBikeSimpleDTO::new)
                .toList();
    }

    public EBike findById(String id) {
        EBike eBike = this.eBikeRepository.findById(id).orElse(null);
        declareVehicleState(eBike);
        return eBike;
    }

    public List<EBikeSimpleDTO> findByManufacturerNameOrModel(String keyword) {
        return eBikeRepository.searchByKeyword(keyword)
                .stream()
                .map(EBikeSimpleDTO::new)
                .toList();
    }
    public EBike save(EBikeSimpleDTO eBike) {
        boolean exist = eBikeRepository.existsById(eBike.getId());
        Manufacturer manufacturer = manufacturerRepository.findByName(eBike.getManufacturer());
        if(exist || manufacturer == null){
            return null;
        }
        EBike temp = new EBike(eBike.getId(), manufacturer, eBike.getModel(), eBike.getPurchasePrice(), null, eBike.getMaxRange());
        return eBikeRepository.save(temp);
    }

//    public EBike update(EBike eBike) {
//        EBike temp = eBikeRepository.findById(eBike.getId()).orElse(null);
//        if (temp == null) {
//            return null;
//        }
//        if (eBike.getModel() != null) {
//            temp.setModel(eBike.getModel());
//        }
//        if (eBike.getManufacturer() != null) {
//            temp.setManufacturer(eBike.getManufacturer());
//        }
//        if (eBike.getFailures() != null) {
//            temp.setFailures(eBike.getFailures());
//        }
//        if (eBike.getRentals() != null) {
//            temp.setRentals(eBike.getRentals());
//        }
//        if (eBike.getPicturePath() != null) {
//            temp.setPicturePath(eBike.getPicturePath());
//        }
//        if (eBike.getPurchasePrice() != null) {
//            temp.setPurchasePrice(eBike.getPurchasePrice());
//        }
//        if (eBike.getMaxRange() != null){
//            temp.setMaxRange(eBike.getMaxRange());
//        }
//        return eBikeRepository.save(temp);
//    }


    public boolean deleteById(String id) {
        if (eBikeRepository.existsById(id)) {
            eBikeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EBike saveImage(String id, MultipartFile file) throws IOException {
        EBike eBike = eBikeRepository.findById(id).orElse(null);
        if(eBike == null) return null;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        Path filePath = uploadPath.resolve(id + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")));
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        eBike.setPicturePath(filePath.toAbsolutePath().toString());
        eBikeRepository.save(eBike);

        return eBike;
    }
}