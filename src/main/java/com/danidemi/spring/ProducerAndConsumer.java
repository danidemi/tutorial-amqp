package com.danidemi.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by danidemi on 04/04/2017.
 */
public class ProducerAndConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProducerAndConsumer.class);

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);

        {
            String message = "foo";
            log.info("Sending message: '{}'.", message);
            template.convertAndSend("myqueue", message);
            log.info("Message sent");
        }

        {
            log.info("Reading message");
            String foo = (String) template.receiveAndConvert("myqueue");
            log.info("Message read: '{}'.", foo);
        }
    }

}
