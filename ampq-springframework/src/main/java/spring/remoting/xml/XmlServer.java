package spring.remoting.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlServer {

    private static final Logger log = LoggerFactory.getLogger(XmlServer.class);

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/spring/remoting/xml/server.xml");

    }

}
