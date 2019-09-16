package com.es.example.esdemo.controller;

import com.es.example.esdemo.entity.BookList;
import com.es.example.esdemo.repo.BookListRepository;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsRequest;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RequestMapping("/indice")
@RestController
public class IndiceController {

    @Autowired
    ElasticsearchTemplate template;
    @Autowired
    TransportClient client;

    @RequestMapping(value = "add/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> indexDoc(@PathVariable String name) {
        template.createIndex(name);
        return new ResponseEntity<>("save executed!", HttpStatus.OK);
    }
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll() {
        ActionFuture<IndicesStatsResponse> isr = client.admin().indices().stats(new IndicesStatsRequest().all());
        Map<String, IndexStats> indexStatsMap = isr.actionGet().getIndices();
        Set<String> set = indexStatsMap.keySet();
        return new ResponseEntity(set, HttpStatus.OK);
    }
    @RequestMapping(value = "count", method = RequestMethod.GET)
    public ResponseEntity<Integer> count() {
        ActionFuture<IndicesStatsResponse> isr = client.admin().indices().stats(new IndicesStatsRequest().all());
        Map<String, IndexStats> indexStatsMap = isr.actionGet().getIndices();
        return new ResponseEntity(indexStatsMap.size(), HttpStatus.OK);
    }
    @RequestMapping(value = "delete/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> delete(@PathVariable String name) {
        template.deleteIndex(name);
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
