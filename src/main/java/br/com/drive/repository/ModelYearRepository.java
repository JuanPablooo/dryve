package br.com.drive.repository;

import br.com.drive.entity.ModelYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModelYearRepository extends JpaRepository<ModelYear, UUID> {

}
