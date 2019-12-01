package ru.otus.spring.mongolibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "Books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "name")
    private String name;

    @Field(value = "genre")
    @DBRef
    private Genre genre;

    @Field(value = "author")
    @DBRef
    private Author author;

    @Field(value = "page")
    private Integer page;

    public Book(String name, Genre genre, Author author, int page) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.page = page;
    }

    public Book(String id, String bookName, Author author) {
        this.id = id;
        this.name = bookName;
        this.author = author;
    }
}
