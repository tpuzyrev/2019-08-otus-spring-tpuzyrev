package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findGenresByName(String name);
}
