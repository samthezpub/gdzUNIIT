package com.example.gdzunit.Repositories;

import com.example.gdzunit.Entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedHashSet;
import java.util.List;

public interface VariantRepository extends JpaRepository<Variant, Long> {

    @Query("SELECT v FROM Variant v ORDER BY v.variant_value ASC")
    List<Variant> findAllVariants();
}
