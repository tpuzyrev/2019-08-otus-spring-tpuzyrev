package ru.otus.spring.jdbclibrary.service;

import ru.otus.spring.jdbclibrary.dto.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findGenresByName(String name);
}
