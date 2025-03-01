package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.EScooter;
import net.etfbl.ip.smart_ride_backend.repository.EScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EScooterService {
    private final EScooterRepository eScooterRepository;

    @Autowired
    public EScooterService(EScooterRepository eScooterRepository) {
        this.eScooterRepository = eScooterRepository;
    }

    public List<EScooter> findAll() {
        return this.eScooterRepository.findAll();
    }

    public EScooter findById(String id) {
        return this.eScooterRepository.findById(id).orElse(null);
    }

    public EScooter save(EScooter eScooter) {
        boolean exist = eScooterRepository.existsById(eScooter.getId());
        if (exist) {
            return null;
        }
        return eScooterRepository.save(eScooter);
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


    public boolean deleteById(String id) {
        if (eScooterRepository.existsById(id)) {
            eScooterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}