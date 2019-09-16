package com.es.example.esdemo.controller;

import com.es.example.esdemo.entity.Book;
import com.es.example.esdemo.repo.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/book")
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/add_index", method = RequestMethod.POST)
    public ResponseEntity<String> indexDoc(@RequestBody Book book) {
        System.out.println("book===" + book);
        bookRepository.save(book);
        return new ResponseEntity<>("save executed!", HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll() {
        Iterable<Book> all = bookRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll(Pageable pageable) {
        Iterable<Book> all = bookRepository.findAll(pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Book> getByName(@PathVariable("name") String name) {
        Book book = bookRepository.findByName(name);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    @RequestMapping(value = "/findContentsTitle", method = RequestMethod.GET)
    public ResponseEntity<Book> findContentsTitle(@RequestParam("title") String title) {
        List<Book> books = bookRepository.findByContentsTitle(title);
        return new ResponseEntity(books, HttpStatus.OK);
    }
    @RequestMapping(value = "/findBytags", method = RequestMethod.GET)
    public ResponseEntity<Book> findBytags(@RequestParam("tag") String tag) {
        List<Book> books = bookRepository.findByTags(tag);
        return new ResponseEntity(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book> updateBook(@PathVariable("id") String id,
                                           @RequestBody Book updateBook) {
        Book book = bookRepository.findBookById(id);
        if (StringUtils.isNotBlank(updateBook.getId())) {
            book.setId(updateBook.getId());
        }
        if (StringUtils.isNotBlank(updateBook.getName())) {
            book.setName(updateBook.getName());
        }
        if (StringUtils.isNotBlank(updateBook.getAuthor())) {
            book.setAuthor(updateBook.getAuthor());
        }
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable("id") String id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>("delete execute!", HttpStatus.OK);
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
