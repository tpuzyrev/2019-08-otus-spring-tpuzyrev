package ru.otus.spring.jpalibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.domain.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class GenreDaoJDBC implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Genre genre) {
        if (genre == null) {
            throw new RuntimeException("Genre parameters should no be empty!");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Long genreId = genre.getId();
        if ( genreId == null) {
            jdbc.getJdbcOperations().update(con -> {
                PreparedStatement ps = con.prepareStatement("insert into genres (name) values (?)", new String[] {"id"});
                ps.setString(1, genre.getName());
                return ps;
            }, keyHolder);
            genreId = (Long) keyHolder.getKey();
            genre.setId(genreId);
        }
        return (long) keyHolder.getKey();
    }

    @Override
    public List<Genre> findAllGenres(){
        return jdbc.query(
                "select genre.id as genreid, genre.name as genreName from genres as genre", new GenreMapper()
        );
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select genre.id as genreid, genre.name as genrename from genres as genre" +
                " where genre.id = :id", params, new GenreDaoJDBC.GenreMapper()
        );
    }

    @Override
    public void deleteById(Long id) {
        Optional<Genre> genre = Optional.ofNullable(getById(id));
        if (genre.isPresent()) {
            if (genre.get().getId() != null) {
                jdbc.update("delete from genres where id = :id", Collections.singletonMap("id", genre.get().getId()));
            }
        }
    }

    @Override
    public List<Genre> findGenresByName(String name){
        Map<String, Object> params = Collections.singletonMap("name", name);
        return jdbc.query(
                "select genre.id as genreid, genre.name as genreName from genres as genre where genre.name like :name", params, new GenreMapper()
        );
    }

    public static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Genre genre = new Genre(resultSet.getString("genrename"));
            genre.setId(resultSet.getLong("genreid"));
            return genre;
        }
    }

}
