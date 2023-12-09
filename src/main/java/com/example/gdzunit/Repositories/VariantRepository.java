package com.example.gdzunit.Repositories;

import com.example.gdzunit.Entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Long> {

    @Query("SELECT v FROM Variant v ORDER BY v.variant_value ASC")
    List<Variant> findAllVariants();

    @Query("SELECT v FROM Variant v WHERE v.variant_value = :variant_value")
    Optional<Variant> findVariantByVariantValue(@Param("variant_value") Short variant_value);
}
