package com.laozhang.maxes.dao;

import com.laozhang.maxes.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookDao extends ElasticsearchRepository<Book,String> {
}
