package com.es.example.esdemo.entity;
 
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "listing",type = "salesListing", shards = 3,replicas = 0, refreshInterval = "-1")
public class SalesListingES  implements Serializable {
    @Id
    private Integer id;
 
    @Field(type = FieldType.Text, searchAnalyzer="ik_max_word", analyzer="ik_max_word")
    private String enTitle;
 
    @Field(type = FieldType.Text, searchAnalyzer="ik_max_word", analyzer="ik_max_word")
    private String cnTitle;
 
    @Field(type = FieldType.Integer,store=true)
    private int categoryId;
 
    @Field(type = FieldType.Text, searchAnalyzer="ik_max_word", analyzer="ik_max_word")
    private String description;

}