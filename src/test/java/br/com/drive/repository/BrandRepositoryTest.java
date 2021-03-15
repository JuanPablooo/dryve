package br.com.drive.repository;

import br.com.drive.entity.Brand;
import br.com.drive.util.BrandCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Test for Brand Repository")
class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("Save Brand when succesfull")
    void save_Brand_WhenSuccessful(){
        Brand brandToBeSaved = BrandCreator.createBrandValidWithoutModelsInside();
        Brand brandSaved = this.brandRepository.save(brandToBeSaved);

        Assertions.assertThat(brandSaved).isNotNull();

        Assertions.assertThat(brandSaved.getId()).isNotNull();

        Assertions.assertThat(brandSaved.getName()).isEqualTo(brandToBeSaved.getName());

    }

    @Test
    @DisplayName("FindById Brand when successful")
    void findById_Brand_WhenSuccessful(){

        Brand brandSaved = saveBrandDefault();

        Optional<Brand> optionalBrand = brandRepository.findById(brandSaved.getId());

        Assertions.assertThat(optionalBrand).isNotEmpty();

        Assertions.assertThat(optionalBrand.get().getName())
                .isNotNull().isNotEmpty().isEqualTo(brandSaved.getName());

    }

    @Test
    @DisplayName("FindAll Brand when successful")
    void findByAll_Model_WhenSuccessful(){

        saveBrandDefault();

        List<Brand> optionalModel = brandRepository.findAll();

        Assertions.assertThat(optionalModel).isNotEmpty().hasSize(1);

    }
    public Brand saveBrandDefault(){
        Brand brandToBeSaved = BrandCreator.createBrandValidWithoutModelsInside();
        return this.brandRepository.save(brandToBeSaved);
    }


}