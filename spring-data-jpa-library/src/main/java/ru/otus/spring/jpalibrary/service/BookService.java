package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;

import java.util.List;

public interface BookService {
    List<Book> getBooksByParam(String title, String authorBrief, String genreName);

    Long insertBook(Book book);

    void updateBook(Book book);

    void deleteBook(Long bookId);

    Comment addComment(Book book, String message);

    Book findById(Long bookId);
}
