package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.repository.AuthorRepository;
import ru.otus.spring.jpalibrary.repository.BookRepository;
import ru.otus.spring.jpalibrary.repository.CommentRepository;
import ru.otus.spring.jpalibrary.repository.GenreRepository;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorDao;
    private final CommentRepository commentRepository;

    @Override
    public List<Book> getBooksByParam(String title, String authorBrief, String genreName) {
        if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(title) || !StringUtils.isEmpty(title)){
            return bookRepository.getByParam(title, authorBrief, genreName);
        } else {
            return bookRepository.getAll();
        }
    }

    @Override
    @Transactional
    public Long insertBook(Book book) {
        Long genreId = book.getGenre().getId();
        if (genreId == null) {
            genreRepository.insert(book.getGenre());
        } else {
            Optional<Genre> genre = genreRepository.getById(genreId);
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
        return bookRepository.insert(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookRepository.updateBook(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {

        Optional<Book> bookOpt = bookRepository.getById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.getAuthor() != null && book.getAuthor().getId() != null) {
                authorDao.deleteById(book.getAuthor().getId());
            }
            if (book.getGenre() != null && book.getGenre().getId() != null) {
                genreRepository.deleteById(book.getGenre().getId());
            }
            bookRepository.deleteById(bookId);
        }
    }

    @Override
    @Transactional
    public Comment addComment(Book book, String message) {
        Assert.notNull(book, "The Book must not be null!");
        Comment comment = new Comment(book, message);
        commentRepository.insert(comment);
        Assert.notNull(comment.getId(), "no comment added!");
        return comment;
    }

    @Override
    public Book findById(Long bookId) {
        return bookRepository.getById(bookId).orElseGet(null);
    }

    @Override
    public List<Comment> getComments(Book book) {
        return commentRepository.getCommentsByBook(book);
    }
}
