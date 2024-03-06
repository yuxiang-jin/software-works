package com.yuxiang.jin.librarymanage.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuxiang.jin.librarymanage.domain.Book;

public interface BookDao extends CrudRepository<Book, Integer> {
    @Query("update Book b set b.title = ?1, b.author = ?2, b.price = ?3 where b.id = ?4")
    @Modifying
    Integer updateBook(String title, String author, Double price, Integer id);

}
