package com.laozhang.maxrabitmq.web.spring;

import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

@Slf4j
public class BasicConsumer implements ChannelAwareMessageListener {
    private String name;

    public BasicConsumer(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            byte[] bytes = message.getBody();
            String data = new String(bytes, "utf-8");
            System.out.println(name + " data: " + data + " tagId: " + message.getMessageProperties().getDeliveryTag());
        } catch (Exception e) {
            //logger.error("local cache rabbit mq localQueue error! e: {}", e);
        }
    }
}
