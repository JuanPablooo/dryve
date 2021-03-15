package br.com.drive.util;

import br.com.drive.entity.Model;
import br.com.drive.entity.ModelYear;
import br.com.drive.entity.Vehicle;
import br.com.drive.model.response.VehicleDetailedResponse;

public class VehicleDetailedResponseCreator {
    public static VehicleDetailedResponse createVehicleDetailedResponse(){
        Vehicle vehicle = VehicleCreator.createValidVehicle();
        ModelYear modelYear = vehicle.getModelYear();
        Model model = modelYear.getModel();
        String marca = model.getBrand().getName();
        return VehicleDetailedResponse.builder()
                .ano(modelYear.getYear())
                .marca(marca)
                .modelo(model.getName())
                .placa(vehicle.getLicensePlate())
                .precoAnuncio(vehicle.getAdPrice())
                .precoKbb(vehicle.getKbbPrice())
                .build();

    }
}
