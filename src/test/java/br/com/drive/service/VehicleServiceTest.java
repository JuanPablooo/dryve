package br.com.drive.service;

import br.com.drive.client.KbbRestClient;
import br.com.drive.entity.Brand;
import br.com.drive.entity.Model;
import br.com.drive.entity.Vehicle;
import br.com.drive.mapper.VehicleMapper;
import br.com.drive.model.client.KbbPrice;
import br.com.drive.model.request.VehicleRequest;
import br.com.drive.model.response.VehicleDetailedResponse;
import br.com.drive.model.response.VehicleResponse;
import br.com.drive.repository.VehicleRepository;
import br.com.drive.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for vehicle Service")
class VehicleServiceTest {
    
    @InjectMocks
    private VehicleService vehicleService;
    
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private ModelYearService modelYearService;
    @Mock
    private ModelService modelService;
    @Mock
    private BrandService brandService;
    @Mock
    private KbbRestClient kbbClient;
    @Mock
    private VehicleMapper vehicleMapper;

    @BeforeEach
    void seTup(){
        Vehicle vehicleValid = VehicleCreator.createValidVehicleWithIdAndRelationEntityWithId();
        VehicleResponse vehicleResponse = VehicleResponseCreator.createVehicleResponse();
        VehicleDetailedResponse vehicleDetailedResponse = VehicleDetailedResponseCreator.createVehicleDetailedResponse();

        PageImpl<Vehicle> vehiclePage = new PageImpl<>(List.of(vehicleValid));

        List<VehicleResponse> vehicleResponses = List.of(vehicleResponse);
        List<Vehicle> vehicles = List.of(vehicleValid);

        KbbPrice kbbPrice = KbbPriceCreator.createKbbPriceValid();


        BDDMockito.when( vehicleRepository.findAllVehicleWithFilters(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(SearchFilterParamVehicles.class)))
                .thenReturn(vehiclePage);

        BDDMockito.when( vehicleMapper.vehiclesToVehicleResponses(vehicles) )
                .thenReturn(vehicleResponses);

        BDDMockito.when( vehicleMapper.vehicleToVehicleDetailedResponse(ArgumentMatchers.any(Vehicle.class)) )
                .thenReturn(vehicleDetailedResponse);


        // create
        BDDMockito.when( modelService.getOneOrThrow(ArgumentMatchers.any()) )
                .thenReturn(vehicleValid.getModelYear().getModel());

        BDDMockito.when( brandService.getOneOrThrow(ArgumentMatchers.any()) )
                .thenReturn(vehicleValid.getModelYear().getModel().getBrand());

        BDDMockito.when( modelYearService.getOneByYearAndModelAndBrand(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(Model.class), ArgumentMatchers.any(Brand.class)) )
                .thenReturn(Optional.of(vehicleValid.getModelYear()));

        BDDMockito.when(  kbbClient.getOne(ArgumentMatchers.anyLong()) )
                .thenReturn(new ResponseEntity<>(kbbPrice, HttpStatus.OK));




        Vehicle vehicleWithId = VehicleCreator.createValidVehicleWithIdAndRelationEntityWithId();

        BDDMockito.when(vehicleRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(vehicleWithId));

        BDDMockito.when( vehicleRepository.save(ArgumentMatchers.any(Vehicle.class)) )
                .thenReturn(vehicleWithId);
    }
    
    
    @Test
    @DisplayName("FindById return Vehicle detailed by id when successful")
    void getOneById_ReturnVehicleDetailed_WhenSuccessful(){
        VehicleDetailedResponse vehicleDetailedResponseExpected = VehicleDetailedResponseCreator.createVehicleDetailedResponse();

        Integer expectedAno = vehicleDetailedResponseExpected.getAno();
        String expectedMarca = vehicleDetailedResponseExpected.getMarca();
        String expectedModelo = vehicleDetailedResponseExpected.getModelo();
        String expectedPlaca = vehicleDetailedResponseExpected.getPlaca();
        BigDecimal expectedPrecoAnuncio = vehicleDetailedResponseExpected.getPrecoAnuncio();
        BigDecimal expectedPrecoKbb = vehicleDetailedResponseExpected.getPrecoKbb();

        VehicleDetailedResponse vehicleDetailedResponseFound = vehicleService.getOneDetailed(ArgumentMatchers.any(UUID.class));

        Assertions.assertThat(vehicleDetailedResponseFound).isNotNull();

        Assertions.assertThat(vehicleDetailedResponseFound.getAno()).isNotNull().isEqualTo(expectedAno);

        Assertions.assertThat(vehicleDetailedResponseFound.getMarca()).isNotNull().isEqualTo(expectedMarca);

        Assertions.assertThat(vehicleDetailedResponseFound.getModelo()).isNotNull().isEqualTo(expectedModelo);

        Assertions.assertThat(vehicleDetailedResponseFound.getPlaca()).isNotNull().isEqualTo(expectedPlaca);

        Assertions.assertThat(vehicleDetailedResponseFound.getPrecoAnuncio()).isNotNull().isEqualTo(expectedPrecoAnuncio);

        Assertions.assertThat(vehicleDetailedResponseFound.getPrecoKbb()).isNotNull().isEqualTo(expectedPrecoKbb);

    }
    @Test
    @DisplayName("FindAllWithFilters return Page VehicleResponse when successful")
    void getAllWithFilters_ReturnVehiclePage_WhenSuccessful(){
        VehicleDetailedResponse vehicleDetailedResponseExpected = VehicleDetailedResponseCreator.createVehicleDetailedResponse();

        Page<VehicleResponse> vehiclesResponseFound = vehicleService.getAllVehiclesPageable(PageRequest.of(1, 1), new SearchFilterParamVehicles());
        Assertions.assertThat(vehiclesResponseFound)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(vehiclesResponseFound.toList())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("Save vehicle, create Vehicle and save when successful")
    void createOne_ReturnVehicle_WhenSuccessful(){
        VehicleRequest vehicleToBeSend = VehicleRequestCreator.createVehicleRequest();

        Vehicle vehicleSaved = vehicleService.create(vehicleToBeSend);

        Assertions.assertThat(vehicleSaved).isNotNull();

        Assertions.assertThat(vehicleSaved.getAdPrice())
                .isNotNull()
                .isEqualTo(vehicleToBeSend.getPrecoAnuncio());

        Assertions.assertThat(vehicleSaved.getModelYear()).isNotNull();

        Assertions.assertThat(vehicleSaved.getModelYear().getModel()).isNotNull();

        Assertions.assertThat(vehicleSaved.getModelYear().getModel().getId()).isNotNull().isEqualTo(vehicleToBeSend.getModeloId());

        Assertions.assertThat(vehicleSaved.getModelYear().getModel().getBrand()).isNotNull();

        Assertions.assertThat(vehicleSaved.getModelYear().getModel().getBrand().getId()).isNotNull().isEqualTo(vehicleToBeSend.getMarcaId());

        Assertions.assertThat(vehicleSaved.getModelYear().getYear())
                .isNotNull()
                .isEqualTo(vehicleToBeSend.getAno());

    }
}