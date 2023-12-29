package com.example.gdzunit.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty(message = "Имя не должно быть пустым!")
    @Size(min = 1, message = "Имя не должно быть короче одного символа!")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Выберите изображение!")
    @Column(name = "image_url")
    private String image_url;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Answer> answers;

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", answers=" + answers +
                '}';
    }
}
