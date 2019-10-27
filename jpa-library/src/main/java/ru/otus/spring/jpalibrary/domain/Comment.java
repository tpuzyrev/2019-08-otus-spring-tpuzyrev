package ru.otus.spring.jpalibrary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "COMMENTS")
public class Comment {

    public Comment(Book book, String message) {
        this.book = book;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE")
    String message;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOKID")
    Book book;

    @Column(name = "DATETIME", nullable = false)
    Date datetime = Date.from(Instant.now());

    @Override
    public String toString() {
        return datetime + "\t\t" + message + '\n';
    }
}
