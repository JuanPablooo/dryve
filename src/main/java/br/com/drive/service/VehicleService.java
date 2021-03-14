package br.com.drive.service;

import br.com.drive.client.KbbRestClient;
import br.com.drive.entity.Brand;
import br.com.drive.entity.Model;
import br.com.drive.entity.ModelYear;
import br.com.drive.entity.Vehicle;
import br.com.drive.model.client.KbbPrice;
import br.com.drive.model.request.VehicleRequest;
import br.com.drive.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    public VehicleService(VehicleRepository vRepository, ModelYearService mdYearService,
                          ModelService mdService, BrandService brandService, KbbRestClient kbbClient) {
        this.vehicleRepository = vRepository;
        this.modelYearService = mdYearService;
        this.modelService = mdService;
        this.brandService = brandService;
        this.kbbClient = kbbClient;
    }

    private final VehicleRepository vehicleRepository;
    private final ModelYearService modelYearService;
    private final ModelService modelService;
    private final BrandService brandService;
    private final KbbRestClient kbbClient;

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    public Vehicle create(VehicleRequest vehicleRequest) {
        ModelYear modelYearFound = getModelYearByVehicleRequest(vehicleRequest);
        Optional<KbbPrice> optKbbPrice = kbbClient.getOne(modelYearFound.getKbbId()).getBody();
        KbbPrice kbbPriceFound = optKbbPrice.orElseThrow(()->new IllegalArgumentException("price kbb is not found"));
        Vehicle vehicleToBeSaved = Vehicle.builder()
                .modelYear(modelYearFound)
                .kbbPrice(kbbPriceFound.getPrice())
                .licensePlate(vehicleRequest.getPlaca())
                .adPrice(vehicleRequest.getPrecoAnuncio())
                .build();
        return vehicleRepository.save(vehicleToBeSaved);
    }

    // this method return detail error, but is not the most better on performer
    private ModelYear getModelYearByVehicleRequest(VehicleRequest vehicleRequest){
        log.info("find model by id {}", vehicleRequest.getModeloId());
        Model modelFound = modelService.getOneOrThrow(vehicleRequest.getModeloId());
        log.info("find brand by id {}", vehicleRequest.getMarcaId());
        Brand brandFound = brandService.getOneOrThrow(vehicleRequest.getMarcaId());
        log.info("find modelYear");
        Optional<ModelYear> optModelYear = modelYearService
                .getOneByYearAndModelAndBrand( vehicleRequest.getAno(), modelFound, brandFound) ;
        if( optModelYear.isPresent() ) return optModelYear.get();
        log.info("modelYear not found by year {} or brand not contain model", vehicleRequest.getAno());
        throw new IllegalArgumentException("ano para este modelo e está marca não etá disponivél");
    }

    // this is faster but user not receive detail message response when not found
    private ModelYear getModelYearByVehicleRequestFaster(VehicleRequest vehicleRequest){
        Integer year = vehicleRequest.getAno();
        UUID modelId = vehicleRequest.getModeloId();
        UUID brandId = vehicleRequest.getMarcaId();
        Optional<ModelYear> optModelYearFound = modelYearService.getOneByYearAndModelIdAndBrandId(year, modelId, brandId);
        if( optModelYearFound.isPresent() ) return optModelYearFound.get();
        throw new IllegalArgumentException("os dados do veículo não são validos");
    }

    public Vehicle getOneOrThrow(UUID uuid){
        return vehicleRepository.findById(uuid)
                .orElseThrow();
    }
}
