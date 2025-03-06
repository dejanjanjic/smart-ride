package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.EScooterSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.EScooter;
import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
import net.etfbl.ip.smart_ride_backend.repository.EScooterRepository;
import net.etfbl.ip.smart_ride_backend.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EScooterService extends VehicleService {
    private final EScooterRepository eScooterRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public EScooterService(EScooterRepository eScooterRepository, ManufacturerRepository manufacturerRepository) {
        this.eScooterRepository = eScooterRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<EScooterSimpleDTO> findAll() {
        return this.eScooterRepository.findAll().stream().map(EScooterSimpleDTO::new).toList();
    }

    public EScooter findById(String id) {
        EScooter eScooter = this.eScooterRepository.findById(id).orElse(null);
        declareVehicleState(eScooter);
        return eScooter;
    }

    public EScooter save(EScooterSimpleDTO eScooter) {
        boolean exist = eScooterRepository.existsById(eScooter.getId());
        Manufacturer manufacturer = manufacturerRepository.findByName(eScooter.getManufacturer());
        if (exist || manufacturer == null) {
            return null;
        }
        EScooter temp = new EScooter(eScooter.getId(), manufacturer, eScooter.getModel(), eScooter.getPurchasePrice(), null, eScooter.getMaxSpeed());
        return eScooterRepository.save(temp);
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