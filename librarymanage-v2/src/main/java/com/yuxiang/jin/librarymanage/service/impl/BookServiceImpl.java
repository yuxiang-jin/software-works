package com.yuxiang.jin.librarymanage.service.impl;

import com.yuxiang.jin.librarymanage.dao.BookDao;
import com.yuxiang.jin.librarymanage.domain.Book;
import com.yuxiang.jin.librarymanage.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, timeout = -1)
public class BookServiceImpl implements BookService {
    //依赖注入容器中的BookDao组件
    @Autowired
    private BookDao bookDao;

    @Override
    public Integer addBook(Book book) {
        bookDao.save(book);
        return book.getId();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    //数据库：如果对数据库不太熟悉也没关系，可在dao层使用ArrayList、HashMap进行代替。
   /* @Override
    public List<Book> getAllBooks() {
        Book book1 = new Book("共产党宣言", "马克思", 28.8);
        Book book2 = new Book("Golang", "张江", 98.8);
        Book book3 = new Book("Python编程指南", "陈杨", 108.8);
        Book book4 = new Book("通信原理", "李伟", 98.0);
        return List.of(book1, book2, book3, book4);
    }*/

    @Override
    public void deleteBook(Integer id) {
        bookDao.deleteById(id);
    }

    @Override
    public Integer updateBook(Book book) {
        return bookDao.updateBook(book);
    }
}
