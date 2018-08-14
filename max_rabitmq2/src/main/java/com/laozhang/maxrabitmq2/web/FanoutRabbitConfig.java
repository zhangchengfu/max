package com.laozhang.maxrabitmq2.web;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutRabbitConfig {
    @Bean
    public Queue message() {
        return new Queue("fanout");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingExchange(Queue message, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(message).to(fanoutExchange);
    }

}
