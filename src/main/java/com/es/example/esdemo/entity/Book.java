package com.es.example.esdemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data

@Document(indexName = "book_index", type = "book")
public class Book {
    @Id
    private String id;
    @Field(type = FieldType.Text,fielddata = true)
    private String name;
    @Field(type = FieldType.Text,fielddata = true)
    private String author;
    @Field(type = FieldType.Date,fielddata = true,format = DateFormat.basic_date_time)
    private Date createTime;
    @Field(type = FieldType.Nested,fielddata = true)
    private List<Content> contents;
    @Field(type = FieldType.Nested,fielddata = true)
    private List<String> tags;
    @Data
    public static class Content{
        private Integer page;
        private String title;
    }
}
