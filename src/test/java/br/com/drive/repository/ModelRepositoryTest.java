package br.com.drive.repository;

import br.com.drive.entity.Brand;
import br.com.drive.entity.Model;
import br.com.drive.entity.Vehicle;
import br.com.drive.util.ModelCreator;
import br.com.drive.util.VehicleCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.List;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class ModelRepositoryTest {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("Save Model when succesfull")
    void save_Model_WhenSuccessful(){

        Model modelToBeSaved = ModelCreator.createModelValid();
        Brand brandSaved = saveBrandDefault();
        modelToBeSaved.setBrand(brandSaved);

        Model modelSaved = modelRepository.save(modelToBeSaved);

        Assertions.assertThat(modelSaved).isNotNull();

        Assertions.assertThat(modelSaved.getId()).isNotNull();

        Assertions.assertThat(modelSaved.getName()).isEqualTo(modelToBeSaved.getName());

    }

    @Test
    @DisplayName("FindById Model when successful")
    void findById_Model_WhenSuccessful(){

        Model modelToBeSaved = ModelCreator.createModelValid();
        Brand brandSaved = saveBrandDefault();
        modelToBeSaved.setBrand(brandSaved);

        Model modelSaved = modelRepository.save(modelToBeSaved);

        Optional<Model> optionalModel = modelRepository.findById(modelSaved.getId());

        Assertions.assertThat(optionalModel).isNotEmpty();

        Assertions.assertThat(optionalModel.get().getName())
                .isNotNull().isNotEmpty().isEqualTo(modelSaved.getName());

    }

    @Test
    @DisplayName("FindAll Model when successful")
    void findByAll_Model_WhenSuccessful(){

        Model modelToBeSaved = ModelCreator.createModelValid();
        Brand brandSaved = saveBrandDefault();
        modelToBeSaved.setBrand(brandSaved);

         modelRepository.save(modelToBeSaved);

        List<Model> optionalModel = modelRepository.findAll();

        Assertions.assertThat(optionalModel).isNotEmpty().hasSize(1);

    }

    Brand saveBrandDefault(){
        Vehicle vehicleToBeSaved = VehicleCreator.createValidVehicle();
        return this.brandRepository.save(vehicleToBeSaved.getModelYear().getModel().getBrand());
    }

}