package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.EBike;
import net.etfbl.ip.smart_ride_backend.repository.EBikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EBikeService extends VehicleService{
    private final EBikeRepository eBikeRepository;

    @Autowired
    public EBikeService(EBikeRepository eBikeRepository) {
        this.eBikeRepository = eBikeRepository;
    }

    public List<EBike> findAll() {
        return this.eBikeRepository.findAll();
    }

    public EBike findById(String id) {
        EBike eBike = this.eBikeRepository.findById(id).orElse(null);
        declareVehicleState(eBike);
        return eBike;
    }

    public EBike save(EBike eBike) {
        boolean exist = eBikeRepository.existsById(eBike.getId());
        if (exist) {
            return null;
        }
        return eBikeRepository.save(eBike);
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