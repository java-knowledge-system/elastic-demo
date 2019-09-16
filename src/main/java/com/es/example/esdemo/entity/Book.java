package com.es.example.esdemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Map;

@Data

@Document(indexName = "book_index5", type = "book5")
public class Book {
    @Id
    private String id;
    @Field(fielddata = true)
    private String name;
    private String author;
    private List<Content> contents;
    private List<String> tags;
    @Data
    public static class Content{
        private Integer page;
        private String title;
    }
}
