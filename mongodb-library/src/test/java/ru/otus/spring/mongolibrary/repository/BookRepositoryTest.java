package ru.otus.spring.mongolibrary.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.mongolibrary.config.MongoDBConfig;
import ru.otus.spring.mongolibrary.domain.Author;
import ru.otus.spring.mongolibrary.domain.Book;
import ru.otus.spring.mongolibrary.domain.Comment;
import ru.otus.spring.mongolibrary.domain.Genre;


import java.util.List;
import java.util.Optional;

@DisplayName("Тестирование книжной библиотеки")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MongoDBConfig.class})
@DataMongoTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void count() {
        Assert.assertEquals(3, bookRepository.count());
    }

    @Test
    public void insert() {
        Book Book = prepareBook();
        bookRepository.save(Book);
        String bookId = Book.getId();
        Optional<Book> addedBook = bookRepository.findById(bookId);
        Assert.assertTrue(addedBook.isPresent());
        Assert.assertNotNull(addedBook);
        Book book = addedBook.get();
        Assert.assertNotNull(book.getAuthor());
        Assert.assertNotNull(book.getGenre());
        Assert.assertEquals("Тургенев Иван", book.getAuthor().getName());
        Assert.assertEquals("Роман", book.getGenre().getName());
        Assert.assertEquals("Отцы и дети", book.getName());
        bookRepository.deleteById(bookId);
    }

    @Test
    public void delete() {
        Book Book = prepareBook();
        bookRepository.save(Book);
        String bookId = Book.getId();
        bookRepository.deleteById(bookId);
    }

    @Test
    @DisplayName("Комментарии к книге")
    public void bookComments() {
        Book Book = prepareBook();
        bookRepository.save(Book);
        String bookId = Book.getId();
        Optional<Book> addedBookOpt = bookRepository.findById(bookId);
        Assert.assertTrue(addedBookOpt.isPresent());
        mongoTemplate.save(new Comment(addedBookOpt.get(), "Новый комментарий"));
        List<Comment> commentList = mongoTemplate.find(new Query(Criteria.where("bookId").is(bookId)), Comment.class);
        Assert.assertFalse(commentList.isEmpty());
        bookRepository.deleteById(bookId);
    }

    public Book prepareBook() {

        return new Book("Отцы и дети",
                new Genre("Роман"),
                new Author("Тургенев Иван"),
                120);
    }
}
