package ru.otus.spring.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.mongolibrary.domain.Book;
import ru.otus.spring.mongolibrary.domain.Comment;
import ru.otus.spring.mongolibrary.repository.BookRepository;
import ru.otus.spring.mongolibrary.repository.CommentRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<Book> getBooksByParam(String title, String authorBrief, String genreName) {
        List<Book> books = new ArrayList<>();
        if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(authorBrief) || !StringUtils.isEmpty(genreName)){
            Collection<String> genreIds = new HashSet<>();
            Collection<String> authorIds = new HashSet<>();
            books.addAll(bookRepository.findBooksByNameAndAuthorIdAndGenreId(title, genreIds.isEmpty() ? null : genreIds, authorIds.isEmpty() ? null : authorIds));
        } else {
            books.addAll(bookRepository.findAll());
        }
        return prepareBookInfoList(books);
    }

    private List<Book> prepareBookInfoList(List<Book> books) {
        if (CollectionUtils.isEmpty(books)) {
            return Collections.emptyList();
        }
        List<Book> bookList = new ArrayList<>();
        for (Book book : books) {
            bookList.add(book);
        }
        return bookList;
    }

    @Override
    @Transactional
    public String insertBook(Book book) {
        bookRepository.save(book);
        book.setId(book.getId());
        return book.getId();
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(String bookId) {

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            bookRepository.deleteById(bookId);
            commentRepository.deleteAll(commentRepository.findByBookId(bookId));
        }
    }

    @Override
    @Transactional
    public Comment addComment(Book book, String message) {
        Assert.notNull(book, "The Book must not be null!");
        Comment comment = new Comment(book, message);
        commentRepository.save(comment);
        Assert.notNull(comment.getId(), "no comment added!");
        return comment;
    }

    @Override
    @Transactional
    public String addComment(String bookId, String message) {
        Optional<Book> book = findById(bookId);
        if (!book.isPresent()) {
            return "Книга с ИД=" + bookId + "не найдена!";
        }
        Comment comment = addComment(book.get(), message);
        return "The new comment was added with id = " + comment.getId();
    }

    @Override
    public Optional<Book> findById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public String getComments(String bookId) {
        if (bookId == null) {
            return "Необходимо передать идентификатор книги.";
        }
        Optional<Book> book = findById(bookId);
        if (!book.isPresent()) {
            return "Книга с ИД=" + bookId + "не найдена!";
        }
        List<Comment> commentList = commentRepository.findByBookId(bookId);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Комментарии к книге \"%s\":\n", book.get().getName()));
        if (!commentList.isEmpty()) {
            commentList.forEach(comment -> sb.append("\t").append(comment));
        } else {
            sb.append("вы можете оставить первый комментарий :)");
        }
        return sb.toString();
    }
}
