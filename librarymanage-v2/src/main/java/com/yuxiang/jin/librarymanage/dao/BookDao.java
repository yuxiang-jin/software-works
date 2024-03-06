package com.yuxiang.jin.librarymanage.dao;

import com.yuxiang.jin.librarymanage.domain.Book;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookDao {
    @Insert("insert into book_inf values(null, #{title}, #{author}, #{price})")
    void save(Book book);

    @Select("select book_id id, book_title title, book_author author, book_price price from book_inf")
    List<Book> findAll();

    @Delete("delete from book_inf where book_id=#{id}")
    void deleteById(Integer id);

    @Update("update book_inf set book_title=#{title}, book_author=#{author}, book_price=#{price} where book_id=#{id}")
    Integer updateBook(Book book);
}
