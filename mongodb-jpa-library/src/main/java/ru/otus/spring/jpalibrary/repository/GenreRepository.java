package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, BigInteger> {

    List<Genre> findByNameIgnoreCase(String name);
}
