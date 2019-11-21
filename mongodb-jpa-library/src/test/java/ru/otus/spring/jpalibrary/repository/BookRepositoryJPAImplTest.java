package ru.otus.spring.jpalibrary.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.domain.Genre;
import ru.otus.spring.jpalibrary.dto.BookInfo;
import ru.otus.spring.jpalibrary.service.BookService;
import ru.otus.spring.jpalibrary.service.BookServiceImpl;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@DisplayName("Тестирование книжной библиотеки")
@RunWith(SpringRunner.class)
@DataMongoTest
@Import(BookServiceImpl.class)
public class BookRepositoryJPAImplTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void count() {
        Assert.assertEquals(0, bookRepository.count());
    }

    @Test
    public void insert() {
        BookInfo bookInfo = prepareBook();
        bookService.insertBook(bookInfo);
        BigInteger bookId = bookInfo.getId();
        BookInfo addedBook = bookService.findById(bookId);
        Assert.assertTrue(bookInfo != null);
        Assert.assertNotNull(addedBook);
        Assert.assertNotNull(addedBook.getAuthor());
        Assert.assertNotNull(addedBook.getGenre());
        Assert.assertEquals("Тургенев Иван", addedBook.getAuthor().getName());
        Assert.assertEquals("Роман", addedBook.getGenre().getName());
        Assert.assertEquals("Отцы и дети", addedBook.getName());
        bookRepository.deleteById(bookId);
    }

    @Test
    public void delete() {
        BookInfo bookInfo = prepareBook();
        bookService.insertBook(bookInfo);
        BigInteger bookId = bookInfo.getId();
        bookRepository.deleteById(bookId);
    }

    @Test
    @DisplayName("Комментарии к книге")
    public void bookComments() {
        BookInfo bookInfo = prepareBook();
        bookService.insertBook(bookInfo);
        BigInteger bookId = bookInfo.getId();
        Optional<Book> addedBookOpt = bookRepository.findById(bookId);
        Assert.assertTrue(addedBookOpt.isPresent());
        commentRepository.save(new Comment(addedBookOpt.get(), "Новый комментарий"));
        List<Comment> commentList = commentRepository.findByBookId(bookId);
        Assert.assertFalse(commentList.isEmpty());
        bookRepository.deleteById(bookId);
    }

    public BookInfo prepareBook() {

        return new BookInfo("Отцы и дети",
                new Genre("Роман"),
                new Author("Тургенев Иван"),
                120);
    }
}
