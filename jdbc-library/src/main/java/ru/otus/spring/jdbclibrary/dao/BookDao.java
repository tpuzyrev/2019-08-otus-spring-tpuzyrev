package ru.otus.spring.jdbclibrary.dao;

import ru.otus.spring.jdbclibrary.dto.Book;

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
