package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.ClientSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Client;
import net.etfbl.ip.smart_ride_backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<ClientSimpleDTO> findAll() {
        return clientRepository
                .findAll()
                .stream()
                .map(ClientSimpleDTO::new)
                .toList();
    }

    public Client add(Client client) {
        if(clientRepository.existsByEmail(client.getEmail()) || clientRepository.existsByUsername(client.getUsername())){
            return null;
        }
        return clientRepository.save(client);
    }

    public ClientSimpleDTO update(ClientSimpleDTO clientSimpleDTO) {
        Client temp = clientRepository.findById(clientSimpleDTO.getId()).orElse(null);

        if (temp == null) {
            return null;
        }

        if (clientSimpleDTO.getUsername() != null) {
            temp.setUsername(clientSimpleDTO.getUsername());
        }
        if (clientSimpleDTO.getFirstName() != null) {
            temp.setFirstName(clientSimpleDTO.getFirstName());
        }
        if (clientSimpleDTO.getLastName() != null) {
            temp.setLastName(clientSimpleDTO.getLastName());
        }
        if (clientSimpleDTO.getRole() != null) {
            temp.setRole(clientSimpleDTO.getRole());
        }
        if (clientSimpleDTO.getIdNumber() != null) {
            temp.setIdNumber(clientSimpleDTO.getIdNumber());
        }
        if (clientSimpleDTO.getEmail() != null) {
            temp.setEmail(clientSimpleDTO.getEmail());
        }
        if (clientSimpleDTO.getPhoneNumber() != null) {
            temp.setPhoneNumber(clientSimpleDTO.getPhoneNumber());
        }
        if (clientSimpleDTO.getDriverLicenseNumber() != null) {
            temp.setDriverLicenseNumber(clientSimpleDTO.getDriverLicenseNumber());
        }
        if (clientSimpleDTO.getDomesticate() != null) {
            temp.setDomesticate(clientSimpleDTO.getDomesticate());
        }
        if (clientSimpleDTO.getBlocked() != null) {
            temp.setBlocked(clientSimpleDTO.getBlocked());
        }

        Client updatedClient = clientRepository.save(temp);

        return new ClientSimpleDTO(updatedClient);
    }
}
