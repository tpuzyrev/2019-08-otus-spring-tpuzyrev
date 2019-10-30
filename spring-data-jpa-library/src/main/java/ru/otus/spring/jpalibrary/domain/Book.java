package ru.otus.spring.jpalibrary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BOOKS")
@Data
@NoArgsConstructor
@NamedEntityGraph(name = "Book.AuthorAndGenre", attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {

    public Book(Long id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GENREID")
    private Genre genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHORID")
    private Author author;

    @Column(name = "PAGE")
    private Integer page;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Book(String name, Genre genre, Author author, int page) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.page = page;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Книга: ").append(name).
                append(", Автор: " ).append(author != null ? author.getName() : "").
                append(", Жанр: ").append(genre != null ? genre.getName() : "").
                append(", Стр.: ").append(page != null ? page : "").
                append(", ID: ").append(id);
        return sb.toString();
    }
}
