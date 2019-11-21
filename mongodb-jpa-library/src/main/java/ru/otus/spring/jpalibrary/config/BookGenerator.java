package ru.otus.spring.jpalibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Genre;
import ru.otus.spring.jpalibrary.dto.BookInfo;
import ru.otus.spring.jpalibrary.service.BookService;

/**
 * @author Пузырев Тимофей
 */
@Component
public class BookGenerator {

    @Autowired
    BookService bookService;

    public void generate() {
        bookService.insertBook(prepareBook("Отцы и дети", "Тургенев Иван", "Роман", 300));
        bookService.insertBook(prepareBook("Тарас Бульба", "Николай Гоголь", "Повесть", 180));
        bookService.insertBook(prepareBook("Дама с собачкой", "Чехов Антон", "Рассказ", 500));
    }

    public BookInfo prepareBook(String bookName, String authorName, String genreName, int pageCount) {
        return new BookInfo(bookName,
                new Genre(genreName),
                new Author(authorName),
                pageCount);
    }
}
