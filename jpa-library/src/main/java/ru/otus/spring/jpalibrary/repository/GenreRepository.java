package ru.otus.spring.jpalibrary.repository;

import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long insert(Genre genre);

    List<Genre> findAllGenres();

    Optional<Genre> getById(Long id);

    void deleteById(Long id);

    List<Genre> findGenresByName(String name);
}
