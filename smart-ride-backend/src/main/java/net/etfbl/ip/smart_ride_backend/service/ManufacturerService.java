package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
import net.etfbl.ip.smart_ride_backend.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository){
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }
    public List<String> findAllNames() {
        return this.manufacturerRepository.findAll().stream().map(Manufacturer::getName).toList();
    }
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id).orElse(null);
    }

    public Manufacturer save(Manufacturer manufacturer) {
        if(manufacturerRepository.existsByName(manufacturer.getName())){
            return null;
        }
        return manufacturerRepository.save(manufacturer);
    }


    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer temp = manufacturerRepository.findById(manufacturer.getId()).orElse(null);
        if(temp == null){
            return null;
        }
        if(manufacturer.getName() != null){
            temp.setName(manufacturer.getName());
        }
        if(manufacturer.getAddress() != null){
            temp.setAddress(manufacturer.getAddress());
        }
        if(manufacturer.getMail() != null){
            temp.setMail(manufacturer.getMail());
        }
        if(manufacturer.getFax() != null){
            temp.setFax(manufacturer.getFax());
        }
        if(manufacturer.getCountry() != null){
            temp.setCountry(manufacturer.getCountry());
        }
        if(manufacturer.getPhone() != null){
            temp.setPhone(manufacturer.getPhone());
        }
        return manufacturerRepository.save(temp);
    }

    public boolean deleteById(Long id) {
        if (manufacturerRepository.existsById(id)) {
            manufacturerRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
