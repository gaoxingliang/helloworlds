package com.example.ch1.ctrl;

import com.example.ch1.config.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookCtrl {

    @Autowired
    private Book book;


    @GetMapping("/book")
    public String mybook() {
        return book.toString();
    }

}
