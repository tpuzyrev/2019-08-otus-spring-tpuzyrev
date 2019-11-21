package ru.otus.spring.jpalibrary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.math.BigInteger;
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
    private BigInteger id;

    String message;

    BigInteger bookId;

    Date datetime = Date.from(Instant.now());

    @Override
    public String toString() {
        return datetime + "\t\t" + message + '\n';
    }
}
