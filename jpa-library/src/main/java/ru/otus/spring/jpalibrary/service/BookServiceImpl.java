package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.dao.AuthorDao;
import ru.otus.spring.jpalibrary.dao.BookDao;
import ru.otus.spring.jpalibrary.dao.CommentDao;
import ru.otus.spring.jpalibrary.dao.GenreDao;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private final CommentDao commentDao;

    @Override
    public List<Book> getBooksByParam(String title, String authorBrief, String genreName) {
        if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(title) || !StringUtils.isEmpty(title)){
            return bookDao.getByParam(title, authorBrief, genreName);
        } else {
            return bookDao.getAll();
        }
    }

    @Override
    @Transactional
    public Long insertBook(Book book) {
        Long genreId = book.getGenre().getId();
        if (genreId == null) {
            genreDao.insert(book.getGenre());
        } else {
            Optional<Genre> genre = genreDao.getById(genreId);
            if (!genre.isPresent()) {
                throw new RuntimeException("Genre was not found by id =" + genreId);
            }
        }
        Long authorId = book.getAuthor().getId();
        if (authorId == null) {
            authorDao.insert(book.getAuthor());
        } else {
            List<Author> authorList = authorDao.getByIds(Collections.singletonList(authorId));
            if (CollectionUtils.isEmpty(authorList)) {
                throw new RuntimeException("Author was not found by id =" + authorId);
            }
        }
        return bookDao.insert(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {

        Optional<Book> bookOpt = bookDao.getById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.getAuthor() != null && book.getAuthor().getId() != null) {
                authorDao.deleteById(book.getAuthor().getId());
            }
            if (book.getGenre() != null && book.getGenre().getId() != null) {
                genreDao.deleteById(book.getGenre().getId());
            }
            bookDao.deleteById(bookId);
        }
    }

    @Override
    @Transactional
    public Comment addComment(Book book, String message) {
        Assert.notNull(book, "The Book must not be null!");
        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setBook(book);
        commentDao.insert(comment);
        Assert.notNull(comment.getId(), "no comment added!");
        return comment;
    }

    @Override
    public Book findById(Long bookId) {
        return bookDao.getById(bookId).orElse(null);
    }

    @Override
    public List<Comment> getComments(Book book) {
        return commentDao.getCommentsByBook(book);
    }
}
