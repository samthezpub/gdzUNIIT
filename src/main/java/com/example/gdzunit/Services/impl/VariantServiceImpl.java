package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Entity.Variant;
import com.example.gdzunit.Exceptions.VariantNotFoundedException;
import com.example.gdzunit.Repositories.VariantRepository;
import com.example.gdzunit.Services.VariantService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
public class VariantServiceImpl implements VariantService {

    private VariantRepository variantRepository;

    public VariantServiceImpl(VariantRepository variantRepository) {
        this.variantRepository = variantRepository;
    }

    @Override
    public void addVariant(Variant variant) {
        variantRepository.save(variant);
    }

    @Override
    public Variant findVariantById(Long id) throws VariantNotFoundedException {
        return variantRepository.findById(id).orElseThrow(() -> new VariantNotFoundedException("Вариант не найден!"));
    }

    @Override
    public List<Variant> findAll() {
        return variantRepository.findAllVariants();
    }


    @Override
    public void deleteVariantById(Long id) {
        variantRepository.deleteById(id);
    }
}
