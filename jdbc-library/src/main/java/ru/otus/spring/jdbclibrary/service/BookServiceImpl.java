package ru.otus.spring.jdbclibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.jdbclibrary.dao.BookDao;
import ru.otus.spring.jdbclibrary.dto.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public List<Book> getBooksByParam(String title, String authorBrief, String genreName) {
        if (!StringUtils.isEmpty(title) || !StringUtils.isEmpty(title) || !StringUtils.isEmpty(title)){
            return bookDao.getByParam(title, authorBrief, genreName);
        } else {
            return bookDao.getAll();
        }
    }

    @Override
    public Long insertBook(Book book) {
        Long bookId = bookDao.insert(book);
        return bookId;
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookDao.deleteById(bookId);
    }
}
