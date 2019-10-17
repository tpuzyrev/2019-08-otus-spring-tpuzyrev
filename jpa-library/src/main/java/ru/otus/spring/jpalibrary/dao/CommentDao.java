package ru.otus.spring.jpalibrary.dao;

import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;

import java.util.List;

public interface CommentDao {

    Long insert(Comment comment);

    List<Comment> getCommentsByBook(Book book);
}
