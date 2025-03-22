package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.ClientSimpleDTO;
import net.etfbl.ip.smart_ride_backend.dto.EmployeeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Client;
import net.etfbl.ip.smart_ride_backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientSimpleDTO>> getAll(){
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("search/{keyword}") ResponseEntity<List<ClientSimpleDTO>> getAllByKeyword(@PathVariable String keyword){
        return ResponseEntity.ok(this.clientService.findByKeyword(keyword));
    }

    @PostMapping
    public ResponseEntity<Client> add(@RequestBody Client client){
        Client temp = clientService.add(client);
        if(client == null){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.ok(temp);
    }

    @PutMapping
    public ResponseEntity<ClientSimpleDTO> update(@RequestBody ClientSimpleDTO clientSimpleDTO){
        ClientSimpleDTO temp = clientService.update(clientSimpleDTO);
        return temp == null ? ResponseEntity.status(409).build() : ResponseEntity.ok(temp);
    }
}
