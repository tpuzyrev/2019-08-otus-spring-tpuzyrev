package ru.otus.spring.jpalibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
@RequiredArgsConstructor
public class CommentRepositoryJPAImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long insert(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public List<Comment> getCommentsByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book =: book", Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }
}
