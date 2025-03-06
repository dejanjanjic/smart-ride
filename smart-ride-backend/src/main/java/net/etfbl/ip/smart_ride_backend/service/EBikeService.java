package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.EBikeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.EBike;
import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
import net.etfbl.ip.smart_ride_backend.repository.EBikeRepository;
import net.etfbl.ip.smart_ride_backend.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EBikeService extends VehicleService{
    private final EBikeRepository eBikeRepository;
    private final ManufacturerRepository manufacturerRepository;

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
}