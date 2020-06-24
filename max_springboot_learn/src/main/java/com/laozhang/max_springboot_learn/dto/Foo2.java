package com.laozhang.max_springboot_learn.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Foo2 {

    @Value("${foo.name}")
    private String name;

    @Value("true")
    private boolean flag;

    @Value("#{2 * 4}")
    private int num;
}
