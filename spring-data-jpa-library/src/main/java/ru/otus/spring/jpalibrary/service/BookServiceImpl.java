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
    public Iterable<Book> getBooksByParam(String title, String authorBrief, String genreName) {
        if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(authorBrief) || !StringUtils.isEmpty(genreName)){
            return bookRepository.findBooksByNameAndAuthor_NameAndGenre_Name(title, authorBrief, genreName);
        } else {
            return bookRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Long insertBook(Book book) {
        Long genreId = book.getGenre().getId();
        if (genreId == null) {
            genreRepository.save(book.getGenre());
        } else {
            Optional<Genre> genre = genreRepository.findById(genreId);
            if (!genre.isPresent()) {
                throw new RuntimeException("Genre was not found by id =" + genreId);
            }
        }
        Long authorId = book.getAuthor().getId();
        if (authorId == null) {
            authorDao.save(book.getAuthor());
        } else {
            Optional<Author> authorOptional = authorDao.findById(authorId);
            if (!authorOptional.isPresent()) {
                throw new RuntimeException("Author was not found by id =" + authorId);
            }
        }
        bookRepository.save(book);
        return book.getId();
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {

        Optional<Book> bookOpt = bookRepository.findById(bookId);
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
        commentRepository.save(comment);
        Assert.notNull(comment.getId(), "no comment added!");
        return comment;
    }

    @Override
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId).orElseGet(null);
    }
}
