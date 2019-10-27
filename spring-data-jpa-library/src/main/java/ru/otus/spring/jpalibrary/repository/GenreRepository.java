package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    Iterable<Genre> findByNameIgnoreCase(String name);
}
