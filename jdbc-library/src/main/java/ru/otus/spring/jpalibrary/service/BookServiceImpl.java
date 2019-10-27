package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.repository.AuthorDao;
import ru.otus.spring.jpalibrary.repository.BookDao;
import ru.otus.spring.jpalibrary.repository.GenreDao;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

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
            Optional<Genre> genre = Optional.ofNullable(genreDao.getById(genreId));
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

        Optional<Book> bookOpt = Optional.ofNullable(bookDao.getById(bookId));
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
}
