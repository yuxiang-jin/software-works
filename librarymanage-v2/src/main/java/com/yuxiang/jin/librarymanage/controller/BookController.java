package com.yuxiang.jin.librarymanage.controller;

import com.yuxiang.jin.librarymanage.domain.Book;
import com.yuxiang.jin.librarymanage.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tip", "欢迎访问图书管理系统");
        return "hello";
    }

    @PostMapping("/addBook")
    public String addBook(Book book, Model model) {
        bookService.addBook(book);
        model.addAttribute("tip", "图书添加成功！");
        return "redirect:listBooks";
    }

    @GetMapping("/listBooks")
    public String list(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "list";
    }

    @GetMapping("/modifyBook")
    public String modification(Model model) {
        model.addAttribute("tip", "欢迎访问图书管理系统");
        return "modify";
    }

    @GetMapping("/deleteBook")
    public String delete(Integer id) {
        bookService.deleteBook(id);
        return "redirect:listBooks";
    }

    @PostMapping("/updateBook")
    public String updateBook(Book book, RedirectAttributes attributes) {
        int updateCount = bookService.updateBook(book);
        if (updateCount > 0) {
            attributes.addFlashAttribute("tip", "图书修改成功！");
            return "redirect:listBooks";
        } else {
            attributes.addFlashAttribute("tip", "图书修改失败，请您重新尝试!");
            return "redirect:modifyBook";
        }
    }

}
