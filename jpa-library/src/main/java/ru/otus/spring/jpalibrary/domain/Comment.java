package ru.otus.spring.jpalibrary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE")
    String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BOOKID")
    Book book;

    @Column(name = "DATETIME", nullable = false)
    Date datetime = Date.from(Instant.now());

    @Override
    public String toString() {
        return datetime + "\t\t" + message + '\n';
    }
}
