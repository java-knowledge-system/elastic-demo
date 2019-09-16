package com.es.example.esdemo.controller;

import com.es.example.esdemo.entity.Book;
import com.es.example.esdemo.repo.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @RequestMapping(value = "find_by_time", method = RequestMethod.GET)
    public ResponseEntity<Book> findByTime(@RequestParam(value = "start",required = false) Date start, @RequestParam(value = "end",required = false)Date end) {
        if(start ==null&&end ==null){
            return new ResponseEntity(bookRepository.findAll(PageRequest.of(0,20)), HttpStatus.OK);
        }else if(start !=null&&end ==null){
            return new ResponseEntity(bookRepository.findByCreateTimeGreaterThanEqual(start.getTime()), HttpStatus.OK);
        }else if(end !=null&&start ==null){
            return new ResponseEntity(bookRepository.findByCreateTimeLessThanEqual(end.getTime()), HttpStatus.OK);
        }
        return new ResponseEntity(bookRepository.findByCreateTimeBetween(start.getTime(),end.getTime()), HttpStatus.OK);
    }

}
