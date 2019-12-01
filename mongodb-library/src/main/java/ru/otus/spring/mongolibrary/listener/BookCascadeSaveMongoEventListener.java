package ru.otus.spring.mongolibrary.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import ru.otus.spring.mongolibrary.domain.Book;

/**
 * @author Пузырев Тимофей
 */
public class BookCascadeSaveMongoEventListener extends AbstractMongoEventListener<Book> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        Book book = event.getSource();
        if (book.getAuthor() != null) {
            mongoOperations.save(book.getAuthor());
        }
        if (book.getGenre() != null) {
            mongoOperations.save(book.getGenre());
        }
    }
}
