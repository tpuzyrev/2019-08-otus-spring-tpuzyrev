package ru.otus.spring.jpalibrary.repository;

import ru.otus.spring.jpalibrary.domain.Book;

import java.util.List;

public interface BookDao {
    int count();

    long insert(Book prepareBook);

    List<Book> getAll();

    Book getById(Long id);

    void deleteById(Long id);

    List<Book> getByParam(String title, String authorBrief, String genreName);

    void updateBook(Book book);
}
