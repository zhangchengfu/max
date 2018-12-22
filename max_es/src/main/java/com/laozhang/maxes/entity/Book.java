package com.laozhang.maxes.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 加上了@Document注解之后，默认情况下这个实体所有的属性都会被建立索引、并且分词
 */
//http://localhost:9200/product
@Document(indexName = "product", type = "book")
public class Book {
    @Id
    private String id;
    private String name;
    private String message;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
