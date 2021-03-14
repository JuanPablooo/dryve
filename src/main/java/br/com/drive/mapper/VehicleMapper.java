package br.com.drive.mapper;

import br.com.drive.entity.Vehicle;
import br.com.drive.model.response.VehicleDetailedResponse;
import br.com.drive.model.response.VehicleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.UUID;

@Mapper
public abstract class VehicleMapper {
    public static final VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    @Mappings({
            @Mapping(target = "placa", source = "licensePlate"),
            @Mapping(target = "precoAnuncio", source = "adPrice"),
            @Mapping(target = "ano", source = "modelYear.year"),
            @Mapping(target = "precoKbb", source = "kbbPrice"),
            @Mapping(target = "modelo", source = "modelYear.model.name"),
            @Mapping(target = "marca", source = "modelYear.model.brand.name"),
    })
    public abstract VehicleDetailedResponse vehicleToVehicleDetailedResponse(Vehicle vehicle);

    @Mappings({
            @Mapping(target = "placa", source = "licensePlate"),
            @Mapping(target = "marca", source = "modelYear.model.brand.name"),
            @Mapping(target = "modelo", source = "modelYear.model.name"),
    })
    public abstract VehicleResponse vehicleToVehicleResponse(Vehicle vehicle);

    public abstract List<VehicleResponse> vehiclesToVehicleResponses(List<Vehicle> vehicles);
}
