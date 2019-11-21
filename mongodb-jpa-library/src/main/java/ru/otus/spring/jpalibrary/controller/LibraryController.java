package ru.otus.spring.jpalibrary.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.config.BookGenerator;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.domain.Genre;
import ru.otus.spring.jpalibrary.dto.BookInfo;
import ru.otus.spring.jpalibrary.service.AuthorService;
import ru.otus.spring.jpalibrary.service.BookService;
import ru.otus.spring.jpalibrary.service.GenreService;

import java.math.BigInteger;
import java.util.List;

@ShellComponent
public class LibraryController {

    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BookGenerator bookGenerator;

    public LibraryController(GenreService genreService, AuthorService authorService, BookService bookService, BookGenerator bookGenerator) {
        this.genreService = genreService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bookGenerator = bookGenerator;
        //Генерация данных
        this.bookGenerator.generate();
    }

    @ShellMethod(value = "Get books", key = {"b", "books"})
    public List<BookInfo> getBooks(
            @ShellOption(defaultValue = "", value = {"n"}, arity = 1) String bookName,
            @ShellOption(defaultValue = "", value = {"a"}, arity = 1) String author,
            @ShellOption(defaultValue = "", value = {"g"}, arity = 1) String genre) {
        return bookService.getBooksByParam(bookName, author, genre);
    }

    @ShellMethod(value = "Add comment for book", key = {"ac", "add comment"})
    @Transactional
    public String addComment(
            @ShellOption(defaultValue = "", value = {"b"}, arity = 1) BigInteger bookId,
            @ShellOption(defaultValue = "", value = {"c"}, arity = 1) String message) {

        return bookService.addComment(bookId, message);
    }

    @ShellMethod(value = "Get all book comments", key = {"bc", "book comments"})
    @Transactional
    public String getCommentsByBook(@ShellOption(defaultValue = "", value = {"b"}, arity = 1) BigInteger bookId) {
        return bookService.getComments(bookId);
    }


    @ShellMethod(value = "Get genres", key = {"g", "genres"})
    public List<Genre> getGenres(@ShellOption(defaultValue = "", value = {"n"}) String name) {
        return (List<Genre>) genreService.findGenresByName(name);
    }

    @ShellMethod(value = "Get authors", key = {"a", "authors"})
    public List<Author> getAuthors(@ShellOption(defaultValue = "", value = {"b"}) String brief) {
        return authorService.findAuthorsByBrief(brief);
    }

    @ShellMethod(value = "Create book", key = {"cb"})
    public BigInteger createBook(
            @ShellOption(defaultValue = "", value = {"ai"}, arity = 1) BigInteger   authorId,
            @ShellOption(defaultValue = "", value = {"ab"}, arity = 1) String authorBrief,
            @ShellOption(                   value = {"n"},  arity = 1) String bookName,
            @ShellOption(defaultValue = "", value = {"g"}            ) String genreName,
            @ShellOption(defaultValue = "", value = {"p"}            ) Integer page
    ){
        if (StringUtils.isEmpty(authorBrief) && authorId == null){
            System.out.println("Необходимо задать автора! Ключ (ab)");
            return null;
        }
        if (StringUtils.isEmpty(bookName)){
            System.out.println("Необходимо задать название книги! Ключ (n)");
            return null;
        }
        Author author = authorService.getAuthor(authorId, authorBrief);
        List<Genre> genres = (List<Genre>) genreService.findGenresByName(genreName);
        if (genres.size() != 1) {
            System.out.println("Не удалось однозначно определить определить жанр!");
            return null;
        }
        BookInfo book = new BookInfo(bookName, genres.iterator().next(), author, page);
        bookService.insertBook(book);
        System.out.println("Book created");
        return book.getId();
    }


    @ShellMethod(value = "Update book", key = {"ub"})
    public void updateBook(
            @ShellOption(                   value = {"id"}, arity = 1) BigInteger id,
            @ShellOption(defaultValue = "", value = {"ab"}, arity = 1) String authorBrief,
            @ShellOption(defaultValue = "", value = {"t"},  arity = 1) String bookName,
            @ShellOption(defaultValue = "", value = {"ai"}, arity = 1) BigInteger  authorId
    ){
        Author author = null;
        if (authorId != null || !StringUtils.isEmpty(authorBrief)){
            author = authorService.getAuthor(authorId, authorBrief);
        }
        if (author == null && !StringUtils.isEmpty(authorBrief)) {
            author = authorService.insert(new Author(authorBrief));
        }
        if (author == null) {
            System.out.println("Не удалось однозначно определить автора книги!");
            return;
        }
        Book book = new Book(id, bookName, author.getId());
        bookService.updateBook(book);
        System.out.println("Book updated");
    }

    @ShellMethod(value = "Delete book", key = {"db"})
    public void deleteBook(BigInteger bookId){
        bookService.deleteBook(bookId);
        System.out.println("Book deleted");
    }
}
