package com.yuxiang.jin.librarymanage.service;

import com.yuxiang.jin.librarymanage.domain.Book;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Integer addBook(Book book);

    List<Book> getAllBooks();

    void deleteBook(Integer id);

    Integer updateBook(Book book);
}
