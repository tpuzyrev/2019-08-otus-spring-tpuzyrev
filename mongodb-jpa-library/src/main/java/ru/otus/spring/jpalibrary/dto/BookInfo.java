package ru.otus.spring.jpalibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.math.BigInteger;

/**
 * @author Пузырев Тимофей
 */
@Data
@AllArgsConstructor
public class BookInfo {

    private BigInteger id;

    private String name;

    private Genre genre;

    private Author author;

    private Integer page;

    public BookInfo(String bookName, Genre genre, Author author, Integer page) {
        this.name = bookName;
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

    public Book getBook() {
        return new Book(id,
                name,
                genre == null ? null : genre.getId(),
                author == null ? null : author.getId(),
                page
        );
    }
}
