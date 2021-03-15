package br.com.drive.util;

import br.com.drive.entity.ModelYear;
import br.com.drive.entity.Vehicle;

import java.math.BigDecimal;
import java.util.UUID;

public class VehicleCreator {

    public static Vehicle createValidVehicle(){
        ModelYear modelYearValid = ModelYearCreator.createModelYearValid();
        return Vehicle.builder()
                .adPrice(BigDecimal.valueOf(5000))
                .kbbPrice(BigDecimal.valueOf(5700))
                .licensePlate("XYZ-1234")
                .modelYear(modelYearValid)
                .build();
    }

    public static Vehicle createValidVehicleWithRelationEntityWithId(){
        ModelYear modelYearValid = ModelYearCreator.createModelYearValidWithId();
        return Vehicle.builder()
                .adPrice(BigDecimal.valueOf(5000.00))
                .kbbPrice(BigDecimal.valueOf(5700.00))
                .licensePlate("XYZ-1234")
                .modelYear(modelYearValid)
                .build();
    }
    public static Vehicle createValidVehicleWithIdAndRelationEntityWithId(){
        ModelYear modelYearValid = ModelYearCreator.createModelYearValidWithId();
        return Vehicle.builder()
                .adPrice(BigDecimal.valueOf(5000))
                .kbbPrice(BigDecimal.valueOf(5700))
                .licensePlate("XYZ-1234")
                .modelYear(modelYearValid)
                .build();
    }
}
