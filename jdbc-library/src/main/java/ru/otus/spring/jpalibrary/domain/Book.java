package ru.otus.spring.jpalibrary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {

    private Long id;
    private String name;
    private Genre genre;
    private Author author;
    private Integer page;

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
