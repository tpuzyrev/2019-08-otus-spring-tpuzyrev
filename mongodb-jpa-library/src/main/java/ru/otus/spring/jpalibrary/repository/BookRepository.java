package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Book;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, BigInteger> {


    List<Book> findBooksByNameAndAuthorIdAndGenreId(String bookName, Collection<BigInteger> authorIds, Collection<BigInteger> genreIds);

}
