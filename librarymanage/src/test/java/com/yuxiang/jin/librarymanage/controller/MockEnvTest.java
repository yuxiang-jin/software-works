package com.yuxiang.jin.librarymanage.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yuxiang.jin.librarymanage.domain.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MockEnvTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {
        //测试index方法
        var result = mvc.perform(MockMvcRequestBuilders.get(new URI("/"))).andReturn().getModelAndView();
        assert result != null;
        Assertions.assertEquals(Map.of("tip", "欢迎访问图书管理系统"), result.getModel());
        Assertions.assertEquals("hello", result.getViewName());
    }

    @ParameterizedTest
    @CsvSource({"高性能MySQL(第2版), Jackson Jin, 148.0", "高可用MySQL(第6版), 靳玉祥, 128.0"})
    public void testAddBook(String title, String author, double price) throws Exception {
        //测试addBook方法
        var result = mvc.perform(MockMvcRequestBuilders.post(new URI("/addBook"))
                .param("title", title)
                .param("author", author)
                .param("price", price + "")).andReturn().getModelAndView();
        assert result != null;
        Assertions.assertEquals("redirect:listBooks", result.getViewName());
    }

    @Test
    public void testList() throws Exception {
        //测试list方法
        var result = mvc.perform(MockMvcRequestBuilders.get(new URI("/listBooks"))).andReturn().getModelAndView();
        assert result != null;
        Assertions.assertEquals("list", result.getViewName());
        List<Book> books = (List<Book>) result.getModel().get("books");
        books.forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 8})
    public void testDelete(Integer id) throws Exception {
        //测试delete方法
        var result = mvc.perform(MockMvcRequestBuilders.get("/deleteBook?id={0}", id)).andReturn().getModelAndView();
        assert result != null;
        Assertions.assertEquals("redirect:listBooks", result.getViewName());
    }

}
