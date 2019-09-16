package com.es.example.esdemo.repo;

import com.es.example.esdemo.entity.SalesListingES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesListingESRepository extends ElasticsearchRepository<SalesListingES, Integer> {

}
 
 
