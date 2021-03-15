package br.com.drive.util;

import br.com.drive.entity.Model;
import br.com.drive.entity.ModelYear;

import java.util.UUID;

public class ModelYearCreator {

    public static ModelYear createModelYearValid(){
        Model modelValid = ModelCreator.createModelValid();
        return ModelYear.builder()
                .kbbId(1L)
                .model(modelValid)
                .year(2020)
                .build();
    }

    public static ModelYear createModelYearValidWithId(){
        Model modelValid = ModelCreator.createModelValidWithId();
        return ModelYear.builder()
                .id(UUID.fromString("9d31e563-9d09-4ce8-8ab5-c5177f51d92f"))
                .kbbId(1L)
                .model(modelValid)
                .year(2020)
                .build();
    }
}
