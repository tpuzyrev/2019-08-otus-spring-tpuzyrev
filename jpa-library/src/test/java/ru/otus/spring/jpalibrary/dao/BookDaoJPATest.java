package ru.otus.spring.jpalibrary.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

//@RunWith(SpringRunner.class)
//@EntityScan("ru.otus.spring.jpalibrary.domain")
//@DataJpaTest
//@Import({BookDaoJPA.class, AuthorDaoJPA.class, GenreDaoJPA.class})
public class BookDaoJPATest {

    @Autowired
    BookDao bookDao;

    @Test
    public void count() {
        Assert.assertTrue(Integer.valueOf(3).equals(bookDao.count()));
    }

    @Test
    public void insert() {
        Long bookId = bookDao.insert(prepareBook());
        Optional<Book> addedBookOpt = bookDao.getById(bookId);
        Assert.assertTrue(addedBookOpt.isPresent());
        Book addedBook = addedBookOpt.get();
        Assert.assertNotNull(addedBook);
        Assert.assertNotNull(addedBook.getAuthor());
        Assert.assertNotNull(addedBook.getGenre());
        Assert.assertEquals("Test Author", addedBook.getAuthor().getName());
        Assert.assertEquals("Test Genre", addedBook.getGenre().getName());
        Assert.assertEquals("Test Book", addedBook.getName());
    }

    @Test
    public void delete() {
        Long bookId = bookDao.insert(prepareBook());
        bookDao.deleteById(bookId);
        count();
    }

    public Book prepareBook() {
        return new Book("Test Book", new Genre("Test Genre"), new Author("Test Author"), 120);
    }
}
