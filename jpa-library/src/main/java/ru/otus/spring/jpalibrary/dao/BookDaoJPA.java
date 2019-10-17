package ru.otus.spring.jpalibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@SuppressWarnings("JpaQlInspection")
@Repository
@RequiredArgsConstructor
public class BookDaoJPA implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count () {
        return Long.valueOf((long) em.createQuery("select count(b) from Book b").getSingleResult()).intValue();
    }

    @Override
    public long insert(Book book) {
        em.persist(book);
        return book.getId();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> genreTypedQuery = em.createQuery("select b from Book b", Book.class);
        return genreTypedQuery.getResultList();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Book> bookOpt = getById(id);
        if (bookOpt.isPresent()) {
            em.remove(bookOpt.get());
        } else {
            throw new IllegalStateException("The Book was not found by ID = " + id);
        }
    }

    @Override
    public List<Book> getByParam(String title, String authorBrief, String genreName) {
        TypedQuery<Book> genreTypedQuery = em.createQuery("select b from Book b" +
                " left join Genres g on b.genre = g " +
                " left join Author a on b.author = a " +
                " where b.name like :title " +
                " and a.name like :authorBrief" +
                " and g.name like :genreName", Book.class);
        genreTypedQuery.setParameter("title", title);
        genreTypedQuery.setParameter("authorBrief", authorBrief);
        genreTypedQuery.setParameter("genreName", genreName);
        return genreTypedQuery.getResultList();

    }

    @Override
    public void updateBook(Book book) {
        em.merge(book);
    }

}
