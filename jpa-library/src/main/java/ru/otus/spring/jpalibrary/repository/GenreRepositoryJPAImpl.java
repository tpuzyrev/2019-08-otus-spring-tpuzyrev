package ru.otus.spring.jpalibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@RequiredArgsConstructor
@EntityScan
public class GenreRepositoryJPAImpl implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public long insert(Genre genre) {
        em.persist(genre);
        return genre.getId();
    }

    @Override
    public List<Genre> findAllGenres(){
        TypedQuery<Genre> genreTypedQuery = em.createQuery("select g from Genre g", Genre.class);
        return genreTypedQuery.getResultList();
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Genre> genreOpt = getById(id);
        if (genreOpt.isPresent()) {
            em.remove(genreOpt.get());
        } else {
            throw new IllegalStateException("The Genre was not found by ID = " + id);
        }
    }

    @Override
    public List<Genre> findGenresByName(String name){
        TypedQuery<Genre> genreTypedQuery = em.createQuery("select g from Genre g where g.name like :name", Genre.class);
        genreTypedQuery.setParameter("name", name);
        return genreTypedQuery.getResultList();
    }

}
