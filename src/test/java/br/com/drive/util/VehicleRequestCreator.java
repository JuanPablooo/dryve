package br.com.drive.util;

import br.com.drive.entity.Model;
import br.com.drive.entity.ModelYear;
import br.com.drive.entity.Vehicle;
import br.com.drive.model.request.VehicleRequest;

import java.util.UUID;

public class VehicleRequestCreator {
    public static VehicleRequest createVehicleRequest(){
        Vehicle vehicle = VehicleCreator.createValidVehicleWithRelationEntityWithId();
        ModelYear modelYear = vehicle.getModelYear();
        Model model = modelYear.getModel();
        UUID modelId = model.getId();
        UUID marcaId = model.getBrand().getId();
       return VehicleRequest.builder()
                .ano(modelYear.getYear())
                .marcaId(marcaId)
                .modeloId(modelId)
                .placa(vehicle.getLicensePlate())
                .precoAnuncio(vehicle.getAdPrice())
                .build();
    }
}
