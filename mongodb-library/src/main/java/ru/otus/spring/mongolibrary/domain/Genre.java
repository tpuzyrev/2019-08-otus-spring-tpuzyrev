package ru.otus.spring.mongolibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Setter
@Document(collection = "Genres")
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @Field(value = "id")
    private String id;

    @Field(value = "name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
