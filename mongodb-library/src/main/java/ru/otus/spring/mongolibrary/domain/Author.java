package ru.otus.spring.mongolibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "Authors")
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "name")
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
