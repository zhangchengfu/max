package com.laozhang.maxrabitmq.web;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitUtil {
    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        return factory;
    }
}
