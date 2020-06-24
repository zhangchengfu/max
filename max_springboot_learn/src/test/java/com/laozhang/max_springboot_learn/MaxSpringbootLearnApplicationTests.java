package com.laozhang.max_springboot_learn;

import com.laozhang.max_springboot_learn.dto.Foo;
import com.laozhang.max_springboot_learn.dto.Foo2;
import com.laozhang.max_springboot_learn.dto.Foo3;
import com.laozhang.max_springboot_learn.dto.Foo4;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@Slf4j
class MaxSpringbootLearnApplicationTests {

    @Autowired
    private Foo foo;

    @Autowired
    private Foo2 foo2;

    @Autowired
    private Foo3 foo3;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testFoo() {
        log.info(foo.getName());
    }

    @Test
    public void testFoo2() {
        log.info(foo2.getName());
    }

    @Test
    public void testFoo3() {
        log.info(foo3.getName());
    }

    @Test
    void contextLoads() {
        Foo4 foo4 = (Foo4) context.getBean("foo4");
        log.info(foo4.getName());
    }

    @Test
    public void testRabbitmq() {

        rabbitTemplate.convertAndSend("exchange-test", "queue-test", "test");
    }

    @Test
    public void testRabbitmq2() {

        Object object = rabbitTemplate.receiveAndConvert("queue-test");
    }
}
