package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.model.EBike;
import net.etfbl.ip.smart_ride_backend.repository.EBikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EBikeService {
    private final EBikeRepository eBikeRepository;

    @Autowired
    public EBikeService(EBikeRepository eBikeRepository) {
        this.eBikeRepository = eBikeRepository;
    }

    public List<EBike> findAll() {
        return this.eBikeRepository.findAll();
    }

    public EBike findById(String id) {
        return this.eBikeRepository.findById(id).orElse(null);
    }

    public EBike save(EBike eBike) {
        boolean exist = eBikeRepository.existsById(eBike.getId());
        if (exist) {
            return null;
        }
        return eBikeRepository.save(eBike);
    }

    public EBike update(EBike eBike) {
        EBike temp = eBikeRepository.findById(eBike.getId()).orElse(null);
        return temp == null ? temp : eBikeRepository.save(eBike);
    }

    public boolean deleteById(String id) {
        if (eBikeRepository.existsById(id)) {
            eBikeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}