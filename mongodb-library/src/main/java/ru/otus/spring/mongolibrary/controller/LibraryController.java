package ru.otus.spring.mongolibrary.controller;

import com.github.cloudyrock.mongock.SpringBootMongock;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.mongolibrary.domain.Author;
import ru.otus.spring.mongolibrary.domain.Book;
import ru.otus.spring.mongolibrary.domain.Genre;
import ru.otus.spring.mongolibrary.service.BookService;
import ru.otus.spring.mongolibrary.service.PrintService;

@ShellComponent
public class LibraryController {

    private final BookService bookService;
    private final PrintService printService;
    private final SpringBootMongock springBootMongock;

    public LibraryController( BookService bookService, PrintService printService, SpringBootMongock springBootMongock) {
        this.bookService = bookService;
        this.printService = printService;
        this.springBootMongock = springBootMongock;
        springBootMongock.execute();
    }

    @ShellMethod(value = "Get books", key = {"b", "books"})
    public String getBooks(
            @ShellOption(defaultValue = "", value = {"n"}, arity = 1) String bookName,
            @ShellOption(defaultValue = "", value = {"a"}, arity = 1) String author,
            @ShellOption(defaultValue = "", value = {"g"}, arity = 1) String genre) {
        return printService.printBooks(bookService.getBooksByParam(bookName, author, genre));
    }

    @ShellMethod(value = "Add comment for book", key = {"ac", "add comment"})
    @Transactional
    public String addComment(
            @ShellOption(defaultValue = "", value = {"b"}, arity = 1) String bookId,
            @ShellOption(defaultValue = "", value = {"c"}, arity = 1) String message) {

        return bookService.addComment(bookId, message);
    }

    @ShellMethod(value = "Get all book comments", key = {"bc", "book comments"})
    @Transactional
    public String getCommentsByBook(@ShellOption(defaultValue = "", value = {"b"}, arity = 1) String bookId) {
        return bookService.getComments(bookId);
    }

    @ShellMethod(value = "Create book", key = {"cb"})
    public String createBook(
            @ShellOption(defaultValue = "", value = {"ab"}, arity = 1) String authorBrief,
            @ShellOption(                   value = {"n"},  arity = 1) String bookName,
            @ShellOption(defaultValue = "", value = {"g"}            ) String genreName,
            @ShellOption(defaultValue = "", value = {"p"}            ) Integer page
    ){
        String bookId = bookService.insertBook(new Book(bookName, new Genre(genreName), new Author(authorBrief), page));
        System.out.println("Book created");
        return bookId;
    }


    @ShellMethod(value = "Update book", key = {"ub"})
    public void updateBook(
            @ShellOption(                   value = {"id"}, arity = 1) String id,
            @ShellOption(defaultValue = "", value = {"ab"}, arity = 1) String authorBrief,
            @ShellOption(defaultValue = "", value = {"t"},  arity = 1) String bookName
    ){
        Book book = new Book(id, bookName, new Author(authorBrief));
        bookService.updateBook(book);
        System.out.println("Book updated");
    }

    @ShellMethod(value = "Delete book", key = {"db"})
    public void deleteBook(String bookId){
        bookService.deleteBook(bookId);
        System.out.println("Book deleted");
    }
}
