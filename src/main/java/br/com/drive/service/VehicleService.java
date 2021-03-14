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


    public Vehicle getOneOrThrow(UUID uuid){
        return vehicleRepository.findById(uuid)
                .orElseThrow();
    }
}
