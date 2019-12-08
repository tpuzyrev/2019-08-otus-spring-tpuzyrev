package ru.otus.spring.mongolibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.mongolibrary.domain.Book;


import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findBooksByNameAndAuthorIdAndGenreId(String bookName, Collection<String> authorIds, Collection<String> genreIds);

}
