package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;

public interface GenreService {
    Iterable<Genre> findGenresByName(String name);
}
