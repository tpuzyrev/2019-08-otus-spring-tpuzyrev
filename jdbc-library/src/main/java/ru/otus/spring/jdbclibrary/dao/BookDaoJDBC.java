package ru.otus.spring.jdbclibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.otus.spring.jdbclibrary.dto.Author;
import ru.otus.spring.jdbclibrary.dto.Book;
import ru.otus.spring.jdbclibrary.dto.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class BookDaoJDBC implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count () {
        return jdbc.getJdbcOperations().queryForObject("select count(1) from books", Integer.class);
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.getJdbcOperations().update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into books (name, genreid, authorid, page) values (?, ?, ?, ?)", new String[]{"id"});
            ps.setString(1, book.getName());
            ps.setLong(2, book.getGenre() == null ? null : book.getGenre().getId());
            ps.setLong(3, book.getAuthor() == null ? null : book.getAuthor().getId());
            ps.setInt(4, book.getPage());
            return ps;
        }, keyHolder);
        book.setId((long) keyHolder.getKey());
        return (long) keyHolder.getKey();
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select book.id as bookid, book.name as bookname, author.id as authorid, author.name as authorname, genre.id genreid, genre.name as genrename, book.page as bookpage from books as book" +
                " left join authors as author on book.authorid = author.id " +
                " left join genres as genre on book.genreid = genre.id ", new BookMapper());
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select book.id as bookid, book.name as bookname, author.id as authorid, author.name as authorname, genre.id genreid, genre.name as genrename, book.page as bookpage from books as book" +
                        " left join authors as author on book.authorid = author.id " +
                        " left join genres as genre on book.genreid = genre.id " +
                        " where book.id = :id", params, new BookMapper()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from books where id = :id", Collections.singletonMap("id", id));
    }

    @Override
    public List<Book> getByParam(String title, String authorBrief, String genreName) {
        Map<String, Object> params = new HashMap<>();
        putIsNeed(params, "bookname", title);
        putIsNeed(params, "authorname", authorBrief);
        putIsNeed(params, "genrename", genreName);

        String query = "select book.id as bookid, book.name as bookname, author.id as authorid, author.name as authorname, genre.id genreid, genre.name as genrename, book.page as bookpage from books as book " +                        " from books as book " +
                        " left join authors as author on book.authorid = author.id " +
                        " left join genres as genre on book.genreid = genre.id " +
                        " where 1=1 " +
                        addIsNeed(title,       "and book.title   like :bookname ") +
                        addIsNeed(authorBrief, "and author.name like :authorname ") +
                        addIsNeed(genreName,   "and genre.name like :genrename");
        List<Book> bookList = jdbc.query(query, params, new BookMapper());

        return bookList;
    }

    @Override
    public void updateBook(Book book) {
        Book existBook = getById(book.getId());

        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("name", book.getName() != null ? book.getName() : existBook.getName());
        params.put("authorid", book.getAuthor() != null ? book.getAuthor().getId() : existBook.getAuthor().getId());
        jdbc.update(
                "update books " +
                        "set `name` = :name, " +
                        "    authorid = :authorid " +
                        "where id = :id", params);
    }


    private String addIsNeed(String param, String subQuery) {
        return StringUtils.isEmpty(param) ? "" : subQuery;
    }

    private void putIsNeed(Map<String, Object> params, String name, String value) {
        if (StringUtils.isEmpty(value)){
            return;
        }
        params.put(name, value);
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("bookid");
            String name = resultSet.getString("bookname");
            Genre genre = new GenreDaoJDBC.GenreMapper().mapRow(resultSet, i);
            Author author = new AuthorDaoJDBC.AuthorMapper().mapRow(resultSet, i);
            int page = resultSet.getInt("bookpage");
            Book book = new Book(name, genre, author, page);
            book.setId(id);
            return book;
        }
    }
}
