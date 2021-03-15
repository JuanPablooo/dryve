package br.com.drive.integration;

import br.com.drive.entity.Brand;
import br.com.drive.entity.Model;
import br.com.drive.entity.ModelYear;
import br.com.drive.entity.Vehicle;
import br.com.drive.model.RestPageImpl;
import br.com.drive.model.request.VehicleRequest;
import br.com.drive.model.response.VehicleDetailedResponse;
import br.com.drive.model.response.VehicleResponse;
import br.com.drive.repository.BrandRepository;
import br.com.drive.repository.ModelRepository;
import br.com.drive.repository.ModelYearRepository;
import br.com.drive.repository.VehicleRepository;
import br.com.drive.util.VehicleCreator;
import br.com.drive.util.VehicleRequestCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Profile("test")
public class VehicleControllerIntegration {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ModelRepository  modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelYearRepository modelYearRepository;

    @Test
    @DisplayName("FindById return Vehicle detailed by id when successful")
    void getOneById_ReturnVehicleDetailed_WhenSuccessful(){

        Vehicle vehicleSaved = saveVehicleDefault();

        Integer expectedAno = vehicleSaved.getModelYear().getYear();
        String expectedMarca = vehicleSaved.getModelYear().getModel().getBrand().getName();
        String expectedModelo = vehicleSaved.getModelYear().getModel().getName();
        String expectedPlaca = vehicleSaved.getLicensePlate();
        BigDecimal expectedPrecoAnuncio = vehicleSaved.getAdPrice();
        BigDecimal expectedPrecoKbb = vehicleSaved.getKbbPrice();

        VehicleDetailedResponse vehicleDetailedResponseFound = testRestTemplate.getForObject("/vehicle/{id}", VehicleDetailedResponse.class, vehicleSaved.getId() );

        Assertions.assertThat(vehicleDetailedResponseFound).isNotNull();

        Assertions.assertThat(vehicleDetailedResponseFound.getAno()).isNotNull().isEqualTo(expectedAno);

        Assertions.assertThat(vehicleDetailedResponseFound.getMarca()).isNotNull().isEqualTo(expectedMarca);

        Assertions.assertThat(vehicleDetailedResponseFound.getModelo()).isNotNull().isEqualTo(expectedModelo);

        Assertions.assertThat(vehicleDetailedResponseFound.getPlaca()).isNotNull().isEqualTo(expectedPlaca);

        Assertions.assertThat(vehicleDetailedResponseFound.getPrecoAnuncio().compareTo(expectedPrecoAnuncio));

        Assertions.assertThat(vehicleDetailedResponseFound.getPrecoKbb().compareTo(expectedPrecoKbb));



    }

    @Test
    @DisplayName("FindAll return Page VehicleResponse when successful")
    void getAll_ReturnVehiclePage_WhenSuccessful(){
        Vehicle vehicle = saveVehicleDefault();
        RestPageImpl<VehicleResponse>  vehiclesResponseFound =  testRestTemplate.exchange("/vehicle", HttpMethod.GET, null,
                new ParameterizedTypeReference<RestPageImpl<VehicleResponse>>() {
                }).getBody();

        Assertions.assertThat(vehiclesResponseFound)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(vehiclesResponseFound.toList())
                .isNotNull();
    }

    @Test
    @DisplayName("FindAllWithFilter LicensePlate return Page VehicleResponse when successful")
    void getAllWithFilterLicencePlace_ReturnVehiclePage_WhenSuccessful(){
        Vehicle vehicleSaved = saveVehicleDefault();
        String url = "/vehicle?placa="+vehicleSaved.getLicensePlate();
        RestPageImpl<VehicleResponse>  vehiclesResponseFound =  testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<RestPageImpl<VehicleResponse>>() {
                }).getBody();


        Assertions.assertThat(vehiclesResponseFound)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(vehiclesResponseFound.toList())
                .isNotNull()
                .hasSize(1);
    }
    @Test
    @DisplayName("FindAllWithFilter ModelName return Page VehicleResponse when successful")
    void getAllWithFilterModelName_ReturnVehiclePage_WhenSuccessful(){
        Vehicle vehicleSaved = saveVehicleDefault();
        String url = "/vehicle?modelo="+vehicleSaved.getModelYear().getModel().getName();
        RestPageImpl<VehicleResponse>  vehiclesResponseFound =  testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<RestPageImpl<VehicleResponse>>() {
                }).getBody();


        Assertions.assertThat(vehiclesResponseFound)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(vehiclesResponseFound.toList())
                .isNotNull()
                .hasSize(1);
    }
    @Test
    @DisplayName("FindAllWithFilter  return empty Page VehicleResponse when not found")
    void getAll_ReturnEmptyVehiclePage_WhenSuccessful(){

        String url = "/vehicle";
        RestPageImpl<VehicleResponse>  vehiclesResponseFound =  testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<RestPageImpl<VehicleResponse>>() {
                }).getBody();


        Assertions.assertThat(vehiclesResponseFound)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Save vehicle, create Vehicle and save when successful")
    void createOne_ReturnVehicle_WhenSuccessful(){
        Vehicle vehicleSavedFirst = saveVehicleDefault();

        VehicleRequest vehicleToBeSend = VehicleRequestCreator.createVehicleRequest();
        vehicleToBeSend.setModeloId(vehicleSavedFirst.getModelYear().getModel().getId());
        vehicleToBeSend.setMarcaId(vehicleSavedFirst.getModelYear().getModel().getBrand().getId());
        vehicleToBeSend.setPlaca("WWW-1234");


        ResponseEntity<Vehicle> responseEntityVehicleSaved  = testRestTemplate.postForEntity("/vehicle", vehicleToBeSend, Vehicle.class);


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
    @Test
    @DisplayName("Save Error try create invalid vehicle and save when not found brand or model")
    void tryCreateInvalidVehicle_ReturnNotFound_WhenNotFoundBrandOrModel() {

        VehicleRequest vehicleToBeSend = VehicleRequestCreator.createVehicleRequest();

        ResponseEntity<Vehicle> responseEntityVehicleSaved = testRestTemplate.postForEntity("/vehicle", vehicleToBeSend, Vehicle.class);

        Assertions.assertThat(responseEntityVehicleSaved.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    Vehicle saveVehicleDefault(){
        Vehicle vehicleToBeSaved = VehicleCreator.createValidVehicle();
        ModelYear modelYearToBeSaved = vehicleToBeSaved.getModelYear();
        Model modelToBeSaved = modelYearToBeSaved.getModel();
        Brand brandToBeSaved = modelToBeSaved.getBrand();
        brandRepository.save(vehicleToBeSaved.getModelYear().getModel().getBrand());
        modelRepository.save(vehicleToBeSaved.getModelYear().getModel());
        modelYearRepository.save(modelYearToBeSaved);

        return vehicleRepository.save(vehicleToBeSaved);
    }

}
