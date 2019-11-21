package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.dto.BookInfo;

import java.math.BigInteger;
import java.util.List;

public interface BookService {
    List<BookInfo> getBooksByParam(String title, String authorBrief, String genreName);

    BigInteger insertBook(BookInfo bookInfo);

    void updateBook(Book book);

    void deleteBook(BigInteger bookId);

    Comment addComment(Book book, String message);

    String addComment(BigInteger bookId, String message);

    BookInfo findById(BigInteger bookId);

    String getComments(BigInteger bookId);
}
