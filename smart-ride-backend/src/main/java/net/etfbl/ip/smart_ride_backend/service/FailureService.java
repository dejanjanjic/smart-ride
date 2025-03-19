package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.FailureSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Failure;
import net.etfbl.ip.smart_ride_backend.repository.FailureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailureService {
    private final FailureRepository failureRepository;

    @Autowired
    public FailureService(FailureRepository failureRepository){
        this.failureRepository = failureRepository;
    }

    public List<FailureSimpleDTO> findByVehicleId(String id) {
        return failureRepository
                .findAllByVehicle_Id(id)
                .stream()
                .filter(Failure::getActive)
                .map(FailureSimpleDTO::new)
                .toList();
    }
}
