package ru.otus.spring.jdbclibrary.dto;

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
