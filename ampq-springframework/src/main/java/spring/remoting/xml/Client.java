package spring.remoting.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/spring/remoting/xml/client.xml");

        Echo echo = context.getBean(Echo.class);
        System.out.println(echo.echo("Hello World"));

    }

}
