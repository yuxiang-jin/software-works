package com.yuxiang.jin.librarymanage.service;

import java.util.List;

import com.yuxiang.jin.librarymanage.domain.Book;

public interface BookService {
    Integer addBook(Book book);

    List<Book> getAllBooks();

    void deleteBook(Integer id);

    Integer updateBook(Book book);
}
