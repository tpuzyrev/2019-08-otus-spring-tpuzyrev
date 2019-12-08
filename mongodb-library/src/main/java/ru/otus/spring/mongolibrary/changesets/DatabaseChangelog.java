package ru.otus.spring.mongolibrary.changesets;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.otus.spring.mongolibrary.domain.Author;
import ru.otus.spring.mongolibrary.domain.Book;
import ru.otus.spring.mongolibrary.domain.Genre;
import ru.otus.spring.mongolibrary.repository.BookRepository;

/**
 * @author Пузырев Тимофей
 */
@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "1", author = "tpuzyryov", id = "generateSomeBooks")
    public void generate(BookRepository bookRepository) {
        bookRepository.save(prepareBook("Отцы и дети", "Тургенев Иван", "Роман", 300));
        bookRepository.save(prepareBook("Тарас Бульба", "Николай Гоголь", "Повесть", 180));
        bookRepository.save(prepareBook("Дама с собачкой", "Чехов Антон", "Рассказ", 500));
    }

    public Book prepareBook(String bookName, String authorName, String genreName, int pageCount) {
        return new Book(bookName,
                new Genre(genreName),
                new Author(authorName),
                pageCount);
    }
}
