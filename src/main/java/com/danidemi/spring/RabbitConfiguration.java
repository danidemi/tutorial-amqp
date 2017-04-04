package com.danidemi.spring;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration public class RabbitConfiguration {

    @Bean public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("192.168.99.100");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // pls check how 5672 is mapped by Docker
        factory.setPort(32769);
        return factory;
    }

    @Bean public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean public Queue myQueue() {
        return new Queue("myqueue");
    }
}