package com.es.example.esdemo.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.es.example.esdemo.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {

    Book findByName(String name);
    List<Book> findByAuthor(String author);
    Book findBookById(String id);
    List<Book> findByContentsTitle(String title);
    List<Book> findByTags(String tag);
}
