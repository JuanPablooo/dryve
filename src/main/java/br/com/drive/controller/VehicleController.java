package br.com.drive.controller;

import br.com.drive.entity.Vehicle;
import br.com.drive.model.request.VehicleRequest;
import br.com.drive.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll(){
        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getOne(@PathVariable UUID id){
        return new ResponseEntity<>(vehicleService.getOneOrThrow(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Vehicle> crete(@Valid @RequestBody VehicleRequest vehicleRequest){
        return new ResponseEntity<>(vehicleService.create(vehicleRequest), HttpStatus.CREATED);
    }
}
