package ru.otus.spring.mongolibrary.service;

import ru.otus.spring.mongolibrary.domain.Book;
import ru.otus.spring.mongolibrary.domain.Comment;


import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getBooksByParam(String title, String authorBrief, String genreName);

    String insertBook(Book book);

    void updateBook(Book book);

    void deleteBook(String bookId);

    Comment addComment(Book book, String message);

    String addComment(String bookId, String message);

    Optional<Book> findById(String bookId);

    String getComments(String bookId);
}
