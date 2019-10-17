package ru.otus.spring.jpalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private Long id;
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
