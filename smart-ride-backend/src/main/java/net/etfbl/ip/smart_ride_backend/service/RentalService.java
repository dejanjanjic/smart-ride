package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.RentalDTO;
import net.etfbl.ip.smart_ride_backend.dto.RentalSimpleDTO;
import net.etfbl.ip.smart_ride_backend.dto.StartEndDateTimeDTO;
import net.etfbl.ip.smart_ride_backend.dto.VerticalBarDataDTO;
import net.etfbl.ip.smart_ride_backend.model.*;
import net.etfbl.ip.smart_ride_backend.repository.ClientRepository;
import net.etfbl.ip.smart_ride_backend.repository.RentalRepository;
import net.etfbl.ip.smart_ride_backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, ClientRepository clientRepository, VehicleRepository vehicleRepository){
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
    }
    public List<RentalSimpleDTO> findAll() {
        return rentalRepository
                .findAll()
                .stream()
                .map(RentalSimpleDTO::new)
                .toList();
    }

    public List<RentalSimpleDTO> findAllByVehicleId(String id) {
        return rentalRepository
                .findAllByVehicle_Id(id)
                .stream()
                .map(RentalSimpleDTO::new)
                .toList();
    }

    public RentalDTO add(RentalDTO rentalDTO) {
        Client client = clientRepository.findById(rentalDTO.getClientId()).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(rentalDTO.getVehicleId()).orElse(null);
        if(client == null || vehicle == null){
            return null;
        }
        Rental rental = new Rental(
                rentalDTO.getDateTime(),
                rentalDTO.getStartLocationX(),
                rentalDTO.getStartLocationY(),
                rentalDTO.getEndLocationX(),
                rentalDTO.getEndLocationY(),
                rentalDTO.getDurationInSeconds(),
                rentalDTO.getPrice(),
                rentalDTO.getActive(),
                client,
                vehicle);
        return new RentalDTO(rentalRepository.save(rental));

    }

    public List<VerticalBarDataDTO> getRevenueByDay(StartEndDateTimeDTO startEndDateTimeDTO) {
        List<Rental> rentals = rentalRepository.findAllByDateTimeBetween(startEndDateTimeDTO.getStart(), startEndDateTimeDTO.getEnd());
        LocalDateTime currentDate = startEndDateTimeDTO.getStart();
        List<VerticalBarDataDTO> result = new ArrayList<>();
        String name = "";
        double value = 0.0;
        while (!currentDate.isAfter(startEndDateTimeDTO.getEnd())) {
            name = currentDate.format(DateTimeFormatter.ofPattern("dd.MM."));
            int dayOfYear = currentDate.getDayOfYear();
            value = rentals
                    .stream()
                    .filter(rental -> rental.getDateTime().getDayOfYear() == dayOfYear)
                    .mapToDouble(rental -> rental.getPrice().doubleValue())
                    .sum();
            result.add(new VerticalBarDataDTO(name, value));
            currentDate = currentDate.plusDays(1);
        }

//        for(Rental r : rentals){
//            result.add(new VerticalBarDataDTO(r));
//        }
        return result;
    }

    public List<VerticalBarDataDTO> getTotalRevenueByVehicleType() {
        List<Object[]> results = rentalRepository.getTotalRevenueByVehicleType();
        List<VerticalBarDataDTO> revenueByType = new ArrayList<>();

        for (Object[] result : results) {
            Class<?> vehicleClass = (Class<?>) result[0];
            BigDecimal totalRevenue = (BigDecimal) result[1];

            String vehicleType;
            if (vehicleClass.equals(Car.class)) {
                vehicleType = "Car";
            } else if (vehicleClass.equals(EBike.class)) {
                vehicleType = "EBike";
            } else if (vehicleClass.equals(EScooter.class)) {
                vehicleType = "EScooter";
            } else {
                vehicleType = "Unknown";
            }

            revenueByType.add(new VerticalBarDataDTO(vehicleType, totalRevenue.doubleValue()));
        }

        return revenueByType;
    }
}
