package spring.remoting.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.remoting.api.Echo;
import spring.remoting.api.EchoImp;

@Configuration
public class JavaServer {

    private static final Logger log = LoggerFactory.getLogger(JavaServer.class);
    public static final String QUEUE_NAME = "remotingQueue";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JavaServer.class);
        ctx.refresh();
        ctx.start();
        AmqpInvokerServiceExporter exporter = ctx.getBean(AmqpInvokerServiceExporter.class);


    }

    @Bean EchoImp serviceImplementation() {
        return new EchoImp();
    }

    @Bean ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost("192.168.99.100");
        cf.setPort(32769);
        cf.setUsername("guest");
        cf.setPassword("guest");
        return cf;
    }

    @Bean RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return  rabbitAdmin;
    }

    @Bean Queue queue() {
        Queue name = new Queue(QUEUE_NAME);
        return name;
    }

    @Bean AmqpTemplate amqpTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        return template;
    }

    @Bean AmqpInvokerServiceExporter exporter(Echo implementation, AmqpTemplate template) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setServiceInterface(Echo.class);
        exporter.setService(implementation);
        exporter.setAmqpTemplate(template);
        return exporter;
    }

    @Bean
    SimpleMessageListenerContainer listener(ConnectionFactory facotry, AmqpInvokerServiceExporter listener) {
        // set up the listener and container
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(facotry);

//        Object listener = new Object() {
//            public void handleMessage(String foo) {
//                System.out.println(foo);
//            }
//        };
//        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(listener);
        container.setQueueNames(QUEUE_NAME);
        container.start();
        return container;
    }

}
