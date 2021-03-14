package br.com.drive.repository.custom;

import br.com.drive.entity.Vehicle;
import br.com.drive.util.SearchFilterParamVehicles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomVehicleRepository {
    Page<Vehicle> findAllVehicleWithFilters(Pageable pageable, SearchFilterParamVehicles searFilter);

}
