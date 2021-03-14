package br.com.drive.repository;
import br.com.drive.entity.Vehicle;
import br.com.drive.repository.custom.CustomVehicleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, CustomVehicleRepository {
}
