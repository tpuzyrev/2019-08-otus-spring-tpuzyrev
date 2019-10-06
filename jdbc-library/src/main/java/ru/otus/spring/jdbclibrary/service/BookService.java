package ru.otus.spring.jdbclibrary.service;

import ru.otus.spring.jdbclibrary.dto.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooksByParam(String title, String authorBrief, String genreName);

    Long insertBook(Book book);

    void updateBook(Book book);

    void deleteBook(Long bookId);
}
