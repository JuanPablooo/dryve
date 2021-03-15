package br.com.drive.util;

import br.com.drive.entity.Brand;
import br.com.drive.entity.Model;

import java.util.UUID;

public class ModelCreator {

    public static Model createModelValid(){
        Brand brandValidWithoutModelsInside = BrandCreator.createBrandValidWithoutModelsInside();
        return Model.builder()
                .brand(brandValidWithoutModelsInside)
                .name("TORO 2.0 DIESEL")
                .build();
    }

    public static Model createModelValidWithId(){
        Brand brandValidWithoutModelsInside = BrandCreator.createBrandValidWithoutModelsInsideWithId();
        return Model.builder()
                .id(UUID.fromString("5bc16064-d3ee-4aed-a264-a914233d0c4f"))
                .brand(brandValidWithoutModelsInside)
                .name("TORO 2.0 DIESEL")
                .build();
    }
}
