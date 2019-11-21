package ru.otus.spring.jpalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigInteger;

@Data
@Document(collection = "Books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    public Book(BigInteger id, String name, BigInteger authorId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
    }

    @Id
    private BigInteger id;

    private String name;

    private BigInteger genreId;

    private BigInteger authorId;

    private Integer page;

    public Book(String name, BigInteger genreId, BigInteger authorId, int page) {
        this.name = name;
        this.genreId = genreId;
        this.authorId = authorId;
        this.page = page;
    }

//    @Override
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//        sb.append("Книга: ").append(name).
//                append(", Автор: " ).append(authorId != null ? authorId.getName() : "").
//                append(", Жанр: ").append(genreId != null ? genreId.getName() : "").
//                append(", Стр.: ").append(page != null ? page : "").
//                append(", ID: ").append(id);
//        return sb.toString();
//    }
}
