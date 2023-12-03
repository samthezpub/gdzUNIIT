package com.example.gdzunit.Services;

import com.example.gdzunit.Entity.Variant;
import com.example.gdzunit.Exceptions.VariantNotFoundedException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public interface VariantService {
    void addVariant(Variant variant);
    Variant findVariantById(Long id) throws VariantNotFoundedException;

    List<Variant> findAll();
    void deleteVariantById(Long id);
}
