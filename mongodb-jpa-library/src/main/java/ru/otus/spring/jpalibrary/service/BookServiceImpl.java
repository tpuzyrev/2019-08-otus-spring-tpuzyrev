package ru.otus.spring.jpalibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.dto.BookInfo;
import ru.otus.spring.jpalibrary.repository.AuthorRepository;
import ru.otus.spring.jpalibrary.repository.BookRepository;
import ru.otus.spring.jpalibrary.repository.CommentRepository;
import ru.otus.spring.jpalibrary.repository.GenreRepository;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<BookInfo> getBooksByParam(String title, String authorBrief, String genreName) {
        List<Book> books = new ArrayList<>();
        if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(authorBrief) || !StringUtils.isEmpty(genreName)){
            Collection<BigInteger> genreIds = new HashSet<>();
            Collection<BigInteger> authorIds = new HashSet<>();
            if (!StringUtils.isEmpty(genreName)) {
                List<Genre> genres = genreRepository.findByNameIgnoreCase(genreName);
                genres.forEach(genre -> genreIds.add(genre.getId()));
            }
            if (!StringUtils.isEmpty(authorBrief)) {
                List<Author> authors = authorRepository.findByNameIgnoreCase(authorBrief);
                authors.forEach(author -> authorIds.add(author.getId()));
            }
            books.addAll(bookRepository.findBooksByNameAndAuthorIdAndGenreId(title, genreIds.isEmpty() ? null : genreIds, authorIds.isEmpty() ? null : authorIds));
        } else {
            books.addAll(bookRepository.findAll());
        }
        return prepareBookInfoList(books);
    }

    private List<BookInfo> prepareBookInfoList(List<Book> books) {
        if (CollectionUtils.isEmpty(books)) {
            return Collections.emptyList();
        }
        List<BookInfo> bookInfoList = new ArrayList<>();
        for (Book book : books) {
            bookInfoList.add(prepareBookInfo(book));
        }
        return bookInfoList;
    }

    private BookInfo prepareBookInfo(Book book) {
        Genre genre = null;
        Author author = null;
        if (book.getGenreId() != null) {
            genre = genreRepository.findById(book.getGenreId()).orElseGet(null);
        }
        if (book.getAuthorId() != null) {
            author = authorRepository.findById(book.getAuthorId()).orElseGet(null);
        }
        return new BookInfo(book.getId(), book.getName(), genre, author, book.getPage());

    }

    @Override
    @Transactional
    public BigInteger insertBook(BookInfo bookInfo) {

        Author author = bookInfo.getAuthor();
        if (author != null) {
            if (author.getId() == null) {
                authorRepository.save(author);
            } else {
                Optional<Author> authorFromDb = authorRepository.findById(author.getId());
                authorFromDb.orElseThrow(()->new RuntimeException("Author was not found by id =" + author.getId()));
                bookInfo.setAuthor(authorFromDb.get());
            }
        }
        Genre genre = bookInfo.getGenre();
        if (genre != null) {
            if (genre.getId() == null) {
                genreRepository.save(genre);
            } else {
                Optional<Genre> genreFromDb = genreRepository.findById(genre.getId());
                genreFromDb.orElseThrow(()->new RuntimeException("Genre was not found by id =" + genre.getId()));
                bookInfo.setGenre(genreFromDb.get());
            }
        }
        Book book = bookInfo.getBook();
        bookRepository.save(book);
        bookInfo.setId(book.getId());
        return book.getId();
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(BigInteger bookId) {

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
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
    @Transactional
    public String addComment(BigInteger bookId, String message) {
        BookInfo book = findById(bookId);
        if (book == null) {
            return "Книга с ИД=" + bookId + "не найдена!";
        }
        Comment comment = addComment(book.getBook(), message);
        return "The new comment was added with id = " + comment.getId();
    }

    @Override
    public BookInfo findById(BigInteger bookId) {
        return prepareBookInfo(bookRepository.findById(bookId).orElseGet(null));
    }

    @Override
    public String getComments(BigInteger bookId) {
        if (bookId == null) {
            return "Необходимо передать идентификатор книги.";
        }
        BookInfo book = findById(bookId);
        if (book == null) {
            return "Книга с ИД=" + bookId + "не найдена!";
        }
        List<Comment> commentList = commentRepository.findByBookId(bookId);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Комментарии к книге \"%s\":\n", book.getName()));
        if (!commentList.isEmpty()) {
            commentList.forEach(comment -> sb.append("\t").append(comment));
        } else {
            sb.append("вы можете оставить первый комментарий :)");
        }
        return sb.toString();
    }
}
