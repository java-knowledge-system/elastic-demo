package com.es.example.esdemo.controller;

import com.es.example.esdemo.entity.BookList;
import com.es.example.esdemo.repo.BookListRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book_list")
@RestController
public class BookListController {

    @Autowired
    BookListRepository bookRepository;

    @RequestMapping(value = "/add_index", method = RequestMethod.POST)
    public ResponseEntity<String> indexDoc(@RequestBody BookList book) {
        System.out.println("book_list===" + book);
        bookRepository.save(book);
        return new ResponseEntity<>("save executed!", HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll() {
        Iterable<BookList> all = bookRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll(Pageable pageable) {
        Iterable<BookList> all = bookRepository.findAll(pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @RequestMapping(value = "count", method = RequestMethod.GET)
    public ResponseEntity<Integer> count() {

        return new ResponseEntity(bookRepository.count(), HttpStatus.OK);
    }
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> delete() {
        bookRepository.deleteAll();
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
