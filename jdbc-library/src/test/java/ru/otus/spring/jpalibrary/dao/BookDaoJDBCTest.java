package ru.otus.spring.jpalibrary.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Genre;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({BookDaoJDBC.class, GenreDaoJDBC.class, AuthorDaoJDBC.class})
public class BookDaoJDBCTest {

    @Autowired
    BookDao bookDao;

    @Test
    public void count() {
        Assert.assertTrue(Integer.valueOf(3).equals(bookDao.count()));
    }

    @Test
    public void insert() {
        Long bookId = bookDao.insert(prepareBook());
        Book addedBook = bookDao.getById(bookId);
        Assert.assertNotNull(addedBook);
        Assert.assertNotNull(addedBook.getAuthor());
        Assert.assertNotNull(addedBook.getGenre());
        Assert.assertTrue(addedBook.getAuthor().getName().equals("Test Author"));
        Assert.assertTrue(addedBook.getGenre().getName().equals("Test Genre"));
        Assert.assertTrue(addedBook.getName().equals("Test Book"));
    }

    @Test
    public void delete() {
        insert();
        Long bookId = bookDao.getAll().stream().findAny().get().getId();
        bookDao.deleteById(bookId);
        count();
    }

    public Book prepareBook() {
        return new Book("Test Book", new Genre("Test Genre"), new Author("Test Author"), 120);
    }
}
