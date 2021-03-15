package br.com.drive.repository.custom;

import br.com.drive.entity.Vehicle;
import br.com.drive.util.SearchFilterParamVehicles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomVehicleRepositoryImpl implements CustomVehicleRepository{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<Vehicle> findAllVehicleWithFilters(Pageable pageable, SearchFilterParamVehicles searFilter)    {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        String queryStr = "SELECT v FROM Vehicle v ";
        String condition = " WHERE ";
        if(searFilter.getMarca() != null ){
            queryStr += condition + " v.modelYear.model.brand.name = :marca ";
            condition = " AND ";
        }
        if( searFilter.getMarcaId() != null ){
            queryStr += condition + " v.modelYear.model.brand.id = :marcaId ";
            condition = " AND ";
        }
        if( searFilter.getModelo() != null ){
            queryStr += condition + " v.modelYear.model.name = :modelo ";
            condition = " AND ";
        }
        if( searFilter.getModeloId() != null ){
            queryStr += condition + " v.modelYear.model.id = :modeloId ";
            condition = " AND ";
        }
        if( searFilter.getPlaca() != null ){
            queryStr += condition + " v.licensePlate = :placa ";
        }

        var query = entityManager.createQuery( queryStr, Vehicle.class);

        query = setParametersOfFilters(query, searFilter);

        query.setFirstResult((pageNumber) * pageSize);

        query.setMaxResults(pageSize);

        List <Vehicle> vehicles = query.getResultList();

        var queryTotal = entityManager.createQuery ("SELECT COUNT(v.id) FROM Vehicle v" );
        long countResult = (long)queryTotal.getSingleResult();
        int i= (int)countResult;

        return new PageImpl<>(vehicles, pageable, i);
    }

    public TypedQuery<Vehicle> setParametersOfFilters(TypedQuery<Vehicle> query, SearchFilterParamVehicles searFilter){
        if( searFilter.getMarca() != null ) query.setParameter("marca", searFilter.getMarca() );
        if( searFilter.getMarcaId() != null ) query.setParameter("marcaId", searFilter.getMarcaId() );
        if( searFilter.getModelo() != null ) query.setParameter("modelo", searFilter.getModelo() );
        if( searFilter.getModeloId() != null ) query.setParameter("modeloId", searFilter.getModeloId() );
        if( searFilter.getPlaca() != null ) query.setParameter("placa", searFilter.getPlaca() );
        return query;
    }

}
