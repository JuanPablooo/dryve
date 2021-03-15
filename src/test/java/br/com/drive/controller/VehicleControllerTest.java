package br.com.drive.controller;

import br.com.drive.entity.Brand;
import br.com.drive.entity.Vehicle;
import br.com.drive.model.request.VehicleRequest;
import br.com.drive.model.response.VehicleDetailedResponse;
import br.com.drive.model.response.VehicleResponse;
import br.com.drive.service.VehicleService;
import br.com.drive.util.*;
import lombok.extern.log4j.Log4j2;
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
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DisplayName("Test controller vehicle")
@Log4j2
class VehicleControllerTest {
    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @BeforeEach
    void seTup(){
        Vehicle vehicleValid = VehicleCreator.createValidVehicleWithRelationEntityWithId();
        VehicleResponse vehicleResponse = VehicleResponseCreator.createVehicleResponse();
        VehicleDetailedResponse vehicleDetailedResponse = VehicleDetailedResponseCreator.createVehicleDetailedResponse();

        PageImpl<VehicleResponse> vehiclePage = new PageImpl<>(List.of(vehicleResponse));

        BDDMockito.when( vehicleService.getAllVehiclesPageable(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(SearchFilterParamVehicles.class)))
                .thenReturn(vehiclePage);

        BDDMockito.when(vehicleService.getOneDetailed(ArgumentMatchers.any()))
                .thenReturn(vehicleDetailedResponse);

        BDDMockito.when( vehicleService.create(ArgumentMatchers.any(VehicleRequest.class)) )
                .thenReturn(vehicleValid);
    }


    @Test
    @DisplayName("FindAllWithFilters return Page VehicleResponse when successful")
    void getAllWithFilters_ReturnVehiclePage_WhenSuccessful(){
        VehicleDetailedResponse vehicleDetailedResponseExpected = VehicleDetailedResponseCreator.createVehicleDetailedResponse();

        ResponseEntity <Page<VehicleResponse>> responseEntityVehiclesResponseFound = vehicleController.getAll (PageRequest.of(1, 1), new SearchFilterParamVehicles());

        Assertions.assertThat(responseEntityVehiclesResponseFound).isNotNull();

        Assertions.assertThat(responseEntityVehiclesResponseFound.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Page<VehicleResponse> vehiclesResponseFound = responseEntityVehiclesResponseFound.getBody();

        Assertions.assertThat(vehiclesResponseFound)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(vehiclesResponseFound.toList())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
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

        VehicleDetailedResponse vehicleDetailedResponseFound = vehicleController.getOne(ArgumentMatchers.any(UUID.class)).getBody();

        Assertions.assertThat(vehicleDetailedResponseFound).isNotNull();

        Assertions.assertThat(vehicleDetailedResponseFound.getAno()).isNotNull().isEqualTo(expectedAno);

        Assertions.assertThat(vehicleDetailedResponseFound.getMarca()).isNotNull().isEqualTo(expectedMarca);

        Assertions.assertThat(vehicleDetailedResponseFound.getModelo()).isNotNull().isEqualTo(expectedModelo);

        Assertions.assertThat(vehicleDetailedResponseFound.getPlaca()).isNotNull().isEqualTo(expectedPlaca);

        Assertions.assertThat(vehicleDetailedResponseFound.getPrecoAnuncio()).isNotNull().isEqualTo(expectedPrecoAnuncio);

        Assertions.assertThat(vehicleDetailedResponseFound.getPrecoKbb()).isNotNull().isEqualTo(expectedPrecoKbb);

    }

    @Test
    @DisplayName("Save vehicle, create Vehicle and save when successful")
    void createOne_ReturnVehicle_WhenSuccessful(){
        VehicleRequest vehicleToBeSend = VehicleRequestCreator.createVehicleRequest();

        ResponseEntity<Vehicle> responseEntityVehicleSaved  = vehicleController.crete(vehicleToBeSend);

        Assertions.assertThat(responseEntityVehicleSaved.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Vehicle vehicleSaved  = responseEntityVehicleSaved.getBody();

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