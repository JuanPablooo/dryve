package br.com.drive.service;

import br.com.drive.entity.Brand;
import br.com.drive.exception.ResourceNotFoundException;
import br.com.drive.repository.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BrandService {

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    private final BrandRepository brandRepository;

    protected Brand getOneOrThrow(UUID id){
        return brandRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Brand not found by id "+ id));
    }
}
