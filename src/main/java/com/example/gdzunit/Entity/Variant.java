package com.example.gdzunit.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "variant")
    private Short variant_value;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.MERGE)
    private Set<User> users;

}
