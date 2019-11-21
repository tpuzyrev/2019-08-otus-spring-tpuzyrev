package ru.otus.spring.jpalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Setter
@Document(collection = "Genres")
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    private BigInteger id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
