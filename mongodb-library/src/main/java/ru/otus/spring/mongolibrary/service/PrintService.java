package ru.otus.spring.mongolibrary.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.mongolibrary.domain.Book;

import java.util.Collection;

/**
 * @author Пузырев Тимофей
 */
@Service
public class PrintService {

    public String printBooks(Collection<Book> books) {
        StringBuilder sb = new StringBuilder();
        books.forEach(book -> {
            sb.append("Книга: ").append(book.getName()).
                    append(", Автор: " ).append(book.getAuthor().getName()).
                    append(", Жанр: ").append(book.getGenre().getName()).
                    append(", Стр.: ").append(book.getPage()).
                    append(", ID: ").append(book.getId());
            sb.append("\n");
        });

        return sb.toString();
    }
}
