package ru.otus.spring.jdbclibrary.dao;

import ru.otus.spring.jdbclibrary.dto.Author;

import java.util.Collection;
import java.util.List;

public interface AuthorDao {

    long insert(Author author);

    List<Author> getByIds(Collection<Long> ids);

    List<Author> findAllAuthors();

    List<Author> findAuthorsByName(String brief);

    void deleteById(Long id);
}
