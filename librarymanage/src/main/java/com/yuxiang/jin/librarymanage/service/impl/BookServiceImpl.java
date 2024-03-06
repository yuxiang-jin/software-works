package com.yuxiang.jin.librarymanage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yuxiang.jin.librarymanage.domain.Book;
import com.yuxiang.jin.librarymanage.dao.BookDao;
import com.yuxiang.jin.librarymanage.service.BookService;

@Service
@Transactional(propagation = Propagation.REQUIRED, timeout = -1)
public class BookServiceImpl implements BookService {
    //依赖注入容器中的BookDao组件
    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookDao.findAll();
    }

    @Override
    public Integer addBook(Book book) {
        bookDao.save(book);
        return book.getId();
    }

    @Override
    public void deleteBook(Integer id) {
        bookDao.deleteById(id);
    }

    @Override
    public Integer updateBook(Book book) {
        String title = book.getTitle();
        String author = book.getAuthor();
        Double price = book.getPrice();
        Integer id = book.getId();
        return bookDao.updateBook(title, author, price, id);
    }
}
