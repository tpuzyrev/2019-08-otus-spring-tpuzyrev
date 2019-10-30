package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findByNameIgnoreCase(String name);
}
