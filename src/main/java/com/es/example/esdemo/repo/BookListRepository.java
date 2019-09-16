package com.es.example.esdemo.repo;

import com.es.example.esdemo.entity.BookList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookListRepository extends ElasticsearchRepository<BookList, Integer> {

}
 
 
