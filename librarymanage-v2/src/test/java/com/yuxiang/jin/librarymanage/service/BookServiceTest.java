package com.yuxiang.jin.librarymanage.service;

import com.yuxiang.jin.librarymanage.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void testGetAllBooks() {
        bookService.getAllBooks().forEach(System.out::println);
    }

    @ParameterizedTest
    @CsvSource({"PHP从入门到精通, 程康 , 198.0", "深入理解Java虚拟机(第2版), 刘涛 , 88.0"})
    public void testAddBook(String title, String author, double price) {
        Book book = new Book(title, author, price);
        Integer result = bookService.addBook(book);
        System.out.println(result);
        //Assertions.assertEquals(result, 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 10})
    public void testDeleteBook(Integer id) {
        bookService.deleteBook(id);
    }
}
