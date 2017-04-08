package spring.remoting.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.remoting.api.Echo;


public class JavaClient {

    private static final Logger log = LoggerFactory.getLogger(JavaClient.class);


    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ClientConfig.class);
        ctx.refresh();
        //ctx.start();
        Echo echo = ctx.getBean(Echo.class);
        System.out.println( echo.echo("Ciao") );

    }



}
