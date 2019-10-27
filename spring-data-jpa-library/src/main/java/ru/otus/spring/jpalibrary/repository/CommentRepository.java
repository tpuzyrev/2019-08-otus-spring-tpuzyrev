package ru.otus.spring.jpalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

}
