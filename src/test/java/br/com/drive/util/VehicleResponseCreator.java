package br.com.drive.util;

import br.com.drive.entity.Model;
import br.com.drive.entity.ModelYear;
import br.com.drive.entity.Vehicle;
import br.com.drive.model.response.VehicleResponse;

public class VehicleResponseCreator {
    public static VehicleResponse createVehicleResponse(){
        Vehicle vehicle = VehicleCreator.createValidVehicle();
        ModelYear modelYear = vehicle.getModelYear();
        Model model = modelYear.getModel();
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .marca(model.getBrand().getName())
                .placa(vehicle.getLicensePlate())
                .modelo(model.getName())
                .build();
    }
}
