package com.example.gdzunit.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
//Это энтити с полями id, variant_value, users
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "variant")
    private Short variant_value;


    public Variant() {
    }

    public Variant(Short variant_value) {
        this.variant_value = variant_value;

    }
}
