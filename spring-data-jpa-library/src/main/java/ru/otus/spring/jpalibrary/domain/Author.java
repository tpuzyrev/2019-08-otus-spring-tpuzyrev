package ru.otus.spring.jpalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUTHORS")
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
