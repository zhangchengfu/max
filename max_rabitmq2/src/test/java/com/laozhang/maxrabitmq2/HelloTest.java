package com.laozhang.maxrabitmq2;

import com.laozhang.maxrabitmq2.web.hello.HelloReceiver;
import com.laozhang.maxrabitmq2.web.hello.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTest {
    @Autowired
    private HelloSender helloSender;

    @Test
    public void send() throws Exception {
        helloSender.send();
    }

}
