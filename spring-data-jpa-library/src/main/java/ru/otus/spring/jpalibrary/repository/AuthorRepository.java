package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Iterable<Author> findByNameIgnoreCase(String brief);
}
