package com.laozhang.max_springboot_learn.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "foo")
@Component
public class Foo {

    private int id;
    private String name;
    private Date date;
    private List<String> list;
}
