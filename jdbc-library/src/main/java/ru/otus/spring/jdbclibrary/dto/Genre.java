package ru.otus.spring.jdbclibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private Long id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
