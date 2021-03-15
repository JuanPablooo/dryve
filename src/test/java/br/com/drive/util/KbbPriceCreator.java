package br.com.drive.util;

import br.com.drive.entity.ModelYear;
import br.com.drive.entity.Vehicle;
import br.com.drive.model.client.KbbPrice;

public class KbbPriceCreator {
    public static KbbPrice createKbbPriceValid(){
        Vehicle vehicle = VehicleCreator.createValidVehicle();
        ModelYear modelYearValid = vehicle.getModelYear();
        return KbbPrice.builder()
                .id(modelYearValid.getKbbId())
                .price(vehicle.getAdPrice())
                .build();
    }
}
