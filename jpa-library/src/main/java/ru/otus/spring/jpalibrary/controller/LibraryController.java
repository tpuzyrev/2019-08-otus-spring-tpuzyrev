package ru.otus.spring.jpalibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.spring.jpalibrary.domain.Author;
import ru.otus.spring.jpalibrary.domain.Book;
import ru.otus.spring.jpalibrary.domain.Comment;
import ru.otus.spring.jpalibrary.domain.Genre;
import ru.otus.spring.jpalibrary.service.AuthorService;
import ru.otus.spring.jpalibrary.service.BookService;
import ru.otus.spring.jpalibrary.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryController {

    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;

    @ShellMethod(value = "Get books", key = {"b", "books"})
    public List<Book> getBooks(
            @ShellOption(defaultValue = "", value = {"n"}, arity = 1) String bookName,
            @ShellOption(defaultValue = "", value = {"a"}, arity = 1) String author,
            @ShellOption(defaultValue = "", value = {"g"}, arity = 1) String genre) {
        return bookService.getBooksByParam(bookName, author, genre);
    }

    @ShellMethod(value = "Add comment for book", key = {"ac", "add comment"})
    @Transactional
    public String addComment(
            @ShellOption(defaultValue = "", value = {"b"}, arity = 1) Long bookId,
            @ShellOption(defaultValue = "", value = {"c"}, arity = 1) String message) {

        Book book = bookService.findById(bookId);
        if (book == null) {
            return "Книга с ИД=" + bookId + "не найдена!";
        }
        Comment comment = bookService.addComment(book, message);
        return "The new comment was added with id = " + comment.getId();
    }

    @ShellMethod(value = "Get all book comments", key = {"bc", "book comments"})
    @Transactional
    public String getCommentsByBook(
            @ShellOption(defaultValue = "", value = {"b"}, arity = 1) Long bookId) {
        if (bookId == null) {
            return "";
        }
        Book book = bookService.findById(bookId);
        if (book == null) {
            return "Книга с ИД=" + bookId + "не найдена!";
        }
        List<Comment> commentList = book.getComments();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Комментарии к книге \"%s\":\n", book.getName()));
        if (!commentList.isEmpty()) {
            commentList.forEach(comment -> sb.append("\t").append(comment));
        } else {
            sb.append("вы можете оставить первый комментарий :)");
        }
        return sb.toString();
    }


    @ShellMethod(value = "Get genres", key = {"g", "genres"})
    public List<Genre> getGenres(@ShellOption(defaultValue = "", value = {"n"}) String name) {
        return genreService.findGenresByName(name);
    }

    @ShellMethod(value = "Get authors", key = {"a", "authors"})
    public List<Author> getAuthors(@ShellOption(defaultValue = "", value = {"b"}) String brief) {
        return authorService.findAuthorsByBrief(brief);
    }

    @ShellMethod(value = "Create book", key = {"cb"})
    public Long createBook(
            @ShellOption(defaultValue = "", value = {"ai"}, arity = 1) Long   authorId,
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
        List<Genre> genres = genreService.findGenresByName(genreName);
        if (genres.size() != 1) {
            System.out.println("Не удалось определить жанр!");
            return null;
        }
        Book book = new Book(bookName, genres.iterator().next(), author, page);
        bookService.insertBook(book);
        System.out.println("Book created");
        return book.getId();
    }


    @ShellMethod(value = "Update book", key = {"ub"})
    public void updateBook(
            @ShellOption(                   value = {"id"}, arity = 1) Long   id,
            @ShellOption(defaultValue = "", value = {"ab"}, arity = 1) String authorBrief,
            @ShellOption(defaultValue = "", value = {"t"},  arity = 1) String bookName,
            @ShellOption(defaultValue = "", value = {"ai"}, arity = 1) Long   authorId
    ){
        Author author = null;
        if (authorId != null || !StringUtils.isEmpty(authorBrief)){
            author = authorService.getAuthor(authorId, authorBrief);
        }
        Book book = new Book(id, bookName, author);
        bookService.updateBook(book);
        System.out.println("Book updated");
    }

    @ShellMethod(value = "Delete book", key = {"db"})
    public void deleteBook(Long bookId){
        bookService.deleteBook(bookId);
        System.out.println("Book deleted");
    }
}
