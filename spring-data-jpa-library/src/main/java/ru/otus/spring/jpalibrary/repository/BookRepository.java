package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(value = "Book.AuthorAndGenre", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findBooksByNameAndAuthor_NameAndGenre_Name(String bookName, String authorName, String genreName);

    @Override
    @EntityGraph(value = "Book.AuthorAndGenre", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAll();

    @Override
    @EntityGraph(value = "Book.AuthorAndGenre", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Book> findById(Long aLong);

    @Override
    @EntityGraph(value = "Book.AuthorAndGenre", type = EntityGraph.EntityGraphType.LOAD)
    Iterable<Book> findAllById(Iterable<Long> iterable);
}
