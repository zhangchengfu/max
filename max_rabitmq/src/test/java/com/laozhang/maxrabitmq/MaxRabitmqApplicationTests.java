package com.laozhang.maxrabitmq;

import com.laozhang.maxrabitmq.web.spring.AmqpProducer;
import com.rabbitmq.client.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaxRabitmqApplicationTests {

    //消息队列名称
    private final static String QUEUE_NAME = "hello";

    @Test
    public void send() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //创建连接
        Connection connection = factory.newConnection();
        // 创建消息通道
        Channel channel = connection.createChannel();
        //生成一个消息队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        for (int i = 0; i < 10; i++) {
            String message = "Hello World RabbitMQ count:" + i;

            //发布消息，第一个参数表示路由（Exchange名称），未""则表示使用默认消息路由
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");
        }

        //关闭消息通道和连接
        channel.close();
        connection.close();
    }

    @Test
    public void consumer() throws IOException, InterruptedException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //创建连接
        Connection connection = factory.newConnection();
        // 创建消息通道
        Channel channel = connection.createChannel();
        // 消息队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("[*] Waiting for message. To exist press CTRL+C");

        AtomicInteger count = new AtomicInteger(0);
        //消费者用于获取消息信道绑定的消息队列中的信息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                try {
                    System.out.println(" [x] Received '" + message);
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);

        Thread.sleep(2000);
    }

    @Test
    public void contextLoads() {
    }

    @Autowired
    private AmqpProducer amqpProducer;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testDirectConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"hello.world", "world", "test1"};
        for (int i = 0; i < 10; i++) {
            amqpProducer
                    .publishMsg("direct.exchange", routingKey[i % 3], ">>> hello " + routingKey[i % 3] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }

    @Test
    public void testTopicConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"d.queue", "a.queue", "cqueue"};
        for (int i = 0; i < 20; i++) {
            amqpProducer.publishMsg("topic.exchange", routingKey[i % 3], ">>> hello " + routingKey[i % 3] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }


    @Test
    public void testFanoutConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"d.queue", "a.queue", "cqueue", "hello.world", "world", "test1"};
        for (int i = 0; i < 20; i++) {
            amqpProducer
                    .publishMsg("fanout.exchange", routingKey[i % 6], ">>> hello " + routingKey[i % 6] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }


    @Test
    public void justForTest() {
        System.out.println("-----");
    }

}
