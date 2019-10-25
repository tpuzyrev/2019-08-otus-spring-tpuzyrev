package ru.otus.spring.jpalibrary.repository;

import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Long insert(Comment comment);

    List<Comment> getCommentsByBook(Book book);
}
