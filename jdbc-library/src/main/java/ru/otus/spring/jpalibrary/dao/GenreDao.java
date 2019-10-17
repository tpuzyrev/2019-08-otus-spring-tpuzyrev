package ru.otus.spring.jpalibrary.dao;

import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;

public interface GenreDao {
    long insert(Genre genre);

    List<Genre> findAllGenres();

    Genre getById(Long id);

    void deleteById(Long id);

    List<Genre> findGenresByName(String name);
}
