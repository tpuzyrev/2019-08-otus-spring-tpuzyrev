package ru.otus.spring.jpalibrary.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.jpalibrary.domain.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class AuthorDaoJDBC implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Author author) {
        if (author == null) {
            throw new RuntimeException("Author parameters should no be empty!");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Long authorId = author.getId();
        if ( authorId == null) {
            jdbc.getJdbcOperations().update(con -> {
                PreparedStatement ps = con.prepareStatement("insert into authors (name) values (?)", new String[] {"id"});
                ps.setString(1, author.getName());
                return ps;
            }, keyHolder);
            authorId = (Long) keyHolder.getKey();
            author.setId(authorId);
        }
        return (long) keyHolder.getKey();
    }

    @Override
    public List<Author> getByIds(Collection<Long> ids) {
        List<Author> authorList = new ArrayList<>(ids.size());
        Map<String, Object> params = Collections.singletonMap("ids", new HashSet<>(ids));
        authorList.addAll(jdbc.query(
                "select id as authorid, name as authorName from authors as author  where id in (:ids)", params, new AuthorMapper()
        ));
        return authorList;
    }

    @Override
    public List<Author> findAllAuthors(){
        return jdbc.query(
                "select author.id as authorid, author.name as authorName from authors as author", new AuthorMapper()
        );
    }

    @Override
    public List<Author> findAuthorsByName(String name){
        Map<String, Object> params = Collections.singletonMap("name", "%"+name+"%");
        return jdbc.query(
                "select author.id as authorid, author.name as authorName from authors as author where author.name like :name", params, new AuthorDaoJDBC.AuthorMapper()
        );
    }

    @Override
    public void deleteById(Long id) {
        List<Author> authorList = getByIds(Collections.singletonList(id));
        if (!CollectionUtils.isEmpty(authorList)) {
            Long authorId = authorList.iterator().next().getId();
            if (authorId != null) {
                jdbc.update("delete from authors where id = :id", Collections.singletonMap("id", authorId));
            }
        }
    }

    public static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = new Author( resultSet.getString("authorname"));
            author.setId(resultSet.getLong("authorid"));
            return author;
        }
    }
}
