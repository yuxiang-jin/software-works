package com.yuxiang.jin.librarymanage.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.yuxiang.jin.librarymanage.dao.BookDao;
import com.yuxiang.jin.librarymanage.domain.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MockTest {
    //定义要测试的目标组件: bookService
    @Autowired
    private BookService bookService;

    //为BookService依赖的组件定义一个Mock Bean,该Mock Bean将会被注入被测试的目标组件
    @MockBean
    private BookDao bookDao;

    @Test
    public void testGetAllBooks() {
        //模拟bookDao的findAll()方法的返回值
        BDDMockito.given(this.bookDao.findAll()).willReturn(List.of(
                new Book("测试1", "洪刚", 98.8), new Book("测试2", "陈杨", 98.8)));
        List<Book> result = bookService.getAllBooks();
        Assertions.assertEquals(result.get(0).getTitle(),"测试1");
        Assertions.assertEquals(result.get(0).getAuthor(),"洪刚");
        Assertions.assertEquals(result.get(0).getPrice(),98.8);
        Assertions.assertEquals(result.get(1).getTitle(),"测试2");
        Assertions.assertEquals(result.get(1).getAuthor(),"陈杨");
        Assertions.assertEquals(result.get(1).getPrice(),98.8);
    }

}
