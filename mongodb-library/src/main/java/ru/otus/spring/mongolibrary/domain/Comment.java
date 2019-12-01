package ru.otus.spring.mongolibrary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Date;

@Data
@Document(collection = "Comments")
@NoArgsConstructor
public class Comment {

    public Comment(Book book, String message) {
        this.bookId = book.getId();
        this.message = message;
    }

    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "message")
    String message;

    @Field(value = "bookId")
    String bookId;

    @Field(value = "datetime")
    Date datetime = Date.from(Instant.now());

    @Override
    public String toString() {
        return datetime + "\t\t" + message + '\n';
    }
}
