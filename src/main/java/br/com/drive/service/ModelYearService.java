package br.com.drive.service;

import br.com.drive.entity.Brand;
import br.com.drive.entity.Model;
import br.com.drive.entity.ModelYear;
import br.com.drive.repository.ModelYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ModelYearService {
    @Autowired
    public ModelYearService(ModelYearRepository modelYearRepository) {
        this.modelYearRepository = modelYearRepository;
    }

    private final ModelYearRepository modelYearRepository;

    protected ModelYear getOneOrThrow(UUID uuid){
        return modelYearRepository.findById(uuid)
                .orElseThrow();
    }

    protected Optional<ModelYear> getOneByYearAndModelIdAndBrandId(Integer year, UUID modelId, UUID brandId){
        return modelYearRepository.findByYearAndModelIdAndBrandId(year, modelId, brandId);
    }
    protected Optional<ModelYear> getOneByYearAndModelAndBrand(Integer year, Model model, Brand brand){
        return modelYearRepository.findByYearAndModelIdAndBrandId(year, model.getId(), brand.getId());
    }
}
