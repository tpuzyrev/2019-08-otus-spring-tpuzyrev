package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Comment;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, BigInteger> {

    List<Comment> findByBookId(BigInteger bookId);
}
