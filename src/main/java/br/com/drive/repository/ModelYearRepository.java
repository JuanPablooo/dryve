package br.com.drive.repository;

import br.com.drive.entity.ModelYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelYearRepository extends JpaRepository<ModelYear, UUID> {
    @Query(value =
            "SELECT md FROM ModelYear md " +
                    "WHERE md.year = :year AND md.model.id = :modelId AND md.model.brand.id = :brandId ")
    Optional<ModelYear> findByYearAndModelIdAndBrandId(Integer year, UUID modelId, UUID brandId);
}
