package net.etfbl.ip.smart_ride_backend.service;

import jakarta.transaction.Transactional;
import net.etfbl.ip.smart_ride_backend.dto.FailureSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Failure;
import net.etfbl.ip.smart_ride_backend.model.Vehicle;
import net.etfbl.ip.smart_ride_backend.model.VehicleState;
import net.etfbl.ip.smart_ride_backend.repository.FailureRepository;
import net.etfbl.ip.smart_ride_backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailureService {
    private final FailureRepository failureRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public FailureService(FailureRepository failureRepository, VehicleRepository vehicleRepository){
        this.failureRepository = failureRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<FailureSimpleDTO> findByVehicleId(String id) {
        return failureRepository
                .findAllByVehicle_Id(id)
                .stream()
                .map(FailureSimpleDTO::new)
                .toList();
    }

    public FailureSimpleDTO add(FailureSimpleDTO failureSimpleDTO) {
        Vehicle vehicle = vehicleRepository.findById(failureSimpleDTO.getVehicleId()).orElse(null);
        if(vehicle == null){
            return null;
        }
        Failure failure = failureRepository
                .save(
                        new Failure(
                                failureSimpleDTO.getDescription(),
                                failureSimpleDTO.getDateTime(),
                                vehicle)
                );
        return new FailureSimpleDTO(failure);
    }

    @Transactional
    public boolean deleteById(Long id) {
        Failure failure = this.failureRepository.findById(id).orElse(null);
        if(failure != null){
            Vehicle vehicle = failure.getVehicle();
            if(this.failureRepository.findAllByVehicle_Id(vehicle.getId()).size() == 1){
                vehicle.setVehicleState(VehicleState.AVAILABLE);
                vehicleRepository.save(vehicle);
            }
            this.failureRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
