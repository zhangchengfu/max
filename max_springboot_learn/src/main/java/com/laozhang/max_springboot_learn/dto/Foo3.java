package com.laozhang.max_springboot_learn.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@PropertySource(value = {"classpath:foo3.properties"})
@ConfigurationProperties(prefix = "foo3")
@Component
public class Foo3 {

    private int id;
    private String name;
    private Date date;
    private List<String> list;
}
