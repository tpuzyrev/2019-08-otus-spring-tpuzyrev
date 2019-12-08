package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.domain.Author;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface AuthorService {
    List<Author> findAuthorsByBrief(String brief);

    List<Author> getByIds(Collection<BigInteger> ids);

    Author getAuthor(BigInteger id, String brief);

    Author insert(Author author);
}
