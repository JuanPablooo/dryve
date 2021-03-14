package br.com.drive.service;

import br.com.drive.entity.Model;
import br.com.drive.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
public class ModelService {

    @Autowired
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    private final ModelRepository modelRepository;

    protected Model getOneOrThrow(UUID uuid){
        return modelRepository.findById(uuid)
                .orElseThrow();
    }
}
