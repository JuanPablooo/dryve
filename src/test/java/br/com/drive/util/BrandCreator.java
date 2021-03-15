package br.com.drive.util;

import br.com.drive.entity.Brand;

import java.util.UUID;

public class BrandCreator {
    public static Brand createBrandValidWithoutModelsInside(){
        return Brand.builder()
                .name("FIAT")
                .build();
    }

    public static Brand createBrandValidWithoutModelsInsideWithId(){
        return Brand.builder()
                .id(UUID.fromString("ca43ec74-5bb0-4288-ab11-5df094ca4dc4"))
                .name("FIAT")
                .build();
    }
}
