package br.com.drive.controller;

import br.com.drive.entity.Vehicle;
import br.com.drive.model.request.VehicleRequest;
import br.com.drive.model.response.VehicleDetailedResponse;
import br.com.drive.model.response.VehicleResponse;
import br.com.drive.service.VehicleService;
import br.com.drive.util.SearchFilterParamVehicles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Page<VehicleResponse>> getAll(Pageable pageable, SearchFilterParamVehicles filters){
        //TODO user filters
        return new ResponseEntity<>(vehicleService.getAllVehiclesPageable(pageable, filters), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDetailedResponse> getOne(@PathVariable UUID id){
        return new ResponseEntity<>(vehicleService.getOneDetailed(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Vehicle> crete(@Valid @RequestBody VehicleRequest vehicleRequest){
        return new ResponseEntity<>(vehicleService.create(vehicleRequest), HttpStatus.CREATED);
    }
}
