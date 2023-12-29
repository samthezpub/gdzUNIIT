package com.example.gdzunit.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @org.hibernate.validator.constraints.NotEmpty
    @Column(name = "title")
    private String title;

    @org.hibernate.validator.constraints.NotEmpty
    @Column(name = "content")
    private String content;

    @Column(name = "html")
    private String html;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private Variant variant;

    @Column(name = "all_variants")
    private Boolean isForAllVariants;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;

}
