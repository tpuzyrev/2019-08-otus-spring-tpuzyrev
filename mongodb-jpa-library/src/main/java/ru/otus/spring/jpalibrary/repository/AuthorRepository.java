package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Author;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, BigInteger> {

    List<Author> findByNameIgnoreCase(String brief);
}
