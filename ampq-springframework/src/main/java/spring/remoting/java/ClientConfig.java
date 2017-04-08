package spring.remoting.java;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.remoting.api.Echo;

/**
 * Created by danidemi on 08/04/2017.
 */
@Configuration
public class ClientConfig {
    public static final String QUEUE_NAME = "remotingQueue";
    static CachingConnectionFactory theOnlyCf = null;

    @Bean CachingConnectionFactory connectionFactoryxxx() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost("192.168.99.100");
        cf.setPort(32769);
        cf.setUsername("guest");
        cf.setPassword("guest");
        if(theOnlyCf==null) {
            theOnlyCf = cf;
        }

        return cf;
    }

    @Bean RabbitTemplate amqpTemplatexx() {
        CachingConnectionFactory theOnlyCf = ClientConfig.theOnlyCf;
        RabbitTemplate template = new RabbitTemplate(theOnlyCf);
        template.setRoutingKey("remoting.binding");
        template.setExchange("remoting.exchange");
        return template;
    }

    @Bean RabbitAdmin rabbitAdmin(CachingConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return  rabbitAdmin;
    }

    @Bean Queue queue() {
        Queue name = new Queue(QUEUE_NAME);
        return name;
    }

    @Bean AmqpProxyFactoryBean amqpFactoryBean(AmqpTemplate amqpTemplate) {
        AmqpProxyFactoryBean factoryBean = new AmqpProxyFactoryBean();
        factoryBean.setServiceInterface(Echo.class);
        //factoryBean.setRoutingKey("key");
        factoryBean.setAmqpTemplate(amqpTemplate);
        return factoryBean;
    }

    @Bean Exchange directExchange(Queue someQueue) {
        DirectExchange exchange = new DirectExchange("remoting.exchange");
        Binding binding = BindingBuilder.bind(someQueue).to(exchange).with("remoting.binding");

        return exchange;
    }

}
