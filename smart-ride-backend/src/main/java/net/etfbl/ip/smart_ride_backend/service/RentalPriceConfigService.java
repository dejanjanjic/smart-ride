package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.UpdateRentalPriceDTO;
import net.etfbl.ip.smart_ride_backend.model.RentalPriceConfig;
import net.etfbl.ip.smart_ride_backend.model.VehicleType;
import net.etfbl.ip.smart_ride_backend.repository.RentalPriceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalPriceConfigService {
    private final RentalPriceConfigRepository rentalPriceConfigRepository;

    @Autowired
    public RentalPriceConfigService(RentalPriceConfigRepository rentalPriceConfigRepository){
        this.rentalPriceConfigRepository = rentalPriceConfigRepository;
    }

    public UpdateRentalPriceDTO getAll() {
        return new UpdateRentalPriceDTO(this.rentalPriceConfigRepository.findAll());
    }

    public List<RentalPriceConfig> update(UpdateRentalPriceDTO updateRentalPriceDTO) {
        List<RentalPriceConfig> temp = new ArrayList<>();
        System.out.println(updateRentalPriceDTO);
        if(updateRentalPriceDTO.getCarPrice() != null){
            temp.add(rentalPriceConfigRepository.save(new RentalPriceConfig(VehicleType.CAR, updateRentalPriceDTO.getCarPrice())));
        }
        if(updateRentalPriceDTO.getScooterPrice() != null){
            temp.add(rentalPriceConfigRepository.save(new RentalPriceConfig(VehicleType.E_SCOOTER, updateRentalPriceDTO.getScooterPrice())));
        }
        if(updateRentalPriceDTO.getBikePrice() != null){
            temp.add(rentalPriceConfigRepository.save(new RentalPriceConfig(VehicleType.E_BIKE, updateRentalPriceDTO.getBikePrice())));
        }
        return temp;
    }
}
