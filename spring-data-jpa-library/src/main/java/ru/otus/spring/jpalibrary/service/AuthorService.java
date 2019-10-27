package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.domain.Author;

import java.util.Collection;
import java.util.List;

public interface AuthorService {
    List<Author> findAuthorsByBrief(String brief);

    List<Author> getByIds(Collection<Long> ids);

    Author getAuthor(Long id, String brief);
}
