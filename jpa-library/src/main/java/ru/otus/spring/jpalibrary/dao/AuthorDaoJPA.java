package ru.otus.spring.jpalibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@SuppressWarnings("JpaQlInspection")
@Repository
@RequiredArgsConstructor
public class AuthorDaoJPA implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long insert(Author author) {
        em.persist(author);
        return author.getId();
    }

    @Override
    public List<Author> getByIds(Collection<Long> ids) {
       TypedQuery<Author> query = em.createQuery("select a from Author a where id in (:ids)", Author.class);
       query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<Author> findAllAuthors(){
        TypedQuery<Author> genreTypedQuery = em.createQuery("select a from Author a", Author.class);
        return genreTypedQuery.getResultList();
    }

    @Override
    public List<Author> findAuthorsByName(String name){
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name like :name", Author.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Author> authorOpt = Optional.ofNullable(em.find(Author.class, id));
        if (authorOpt.isPresent()) {
            em.remove(authorOpt.get());
        } else {
            throw new IllegalStateException("The Author was not found by ID = " + id);
        }
    }

}
