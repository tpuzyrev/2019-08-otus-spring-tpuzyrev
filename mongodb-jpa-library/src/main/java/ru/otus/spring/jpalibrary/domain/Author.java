package ru.otus.spring.jpalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Document(collection = "Authors")
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    private BigInteger id;

    private String name;

    public Author(String name) {
        this.name = name;
    }
}
