package ru.otus.spring.jpalibrary.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

@DisplayName("Тестирование книжной библиотеки")
@RunWith(SpringRunner.class)
@DataJpaTest
@Import({BookRepositoryJPAImpl.class, GenreRepositoryJPAImpl.class, AuthorRepositoryJPAImpl.class, CommentRepositoryJPAImpl.class})
public class BookRepositoryJPAImplTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorDao;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void count() {
        Assert.assertTrue(Integer.valueOf(3).equals(bookRepository.count()));
    }

    @Test
    public void insert() {
        Long bookId = bookRepository.insert(prepareBook());
        Optional<Book> addedBookOpt = bookRepository.getById(bookId);
        Assert.assertTrue(addedBookOpt.isPresent());
        Book addedBook = addedBookOpt.get();
        Assert.assertNotNull(addedBook);
        Assert.assertNotNull(addedBook.getAuthor());
        Assert.assertNotNull(addedBook.getGenre());
        Assert.assertEquals("Тургенев Иван", addedBook.getAuthor().getName());
        Assert.assertEquals("Роман", addedBook.getGenre().getName());
        Assert.assertEquals("Отцы и дети", addedBook.getName());
    }

    @Test
    public void delete() {
        Long bookId = bookRepository.insert(prepareBook());
        bookRepository.deleteById(bookId);
        count();
    }

    @Test
    @DisplayName("Комментарии к книге")
    public void bookComments() {
        Optional<Book> addedBookOpt = bookRepository.getById(3L);
        Assert.assertTrue(addedBookOpt.isPresent());
        commentRepository.insert(new Comment(addedBookOpt.get(), "Новый комментарий"));
        List<Comment> commentList = addedBookOpt.get().getComments();
        Assert.assertFalse(commentList.isEmpty());
    }

    public Book prepareBook() {
        Genre genre = genreRepository.findGenresByName("Роман").stream().findAny().get();
        Author author = authorDao.findAuthorsByName("Тургенев Иван").stream().findAny().get();
        return new Book("Отцы и дети", genre, author, 120);
    }
}
