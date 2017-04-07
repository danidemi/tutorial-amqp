package spring.remoting.xml;

public class EchoImp implements Echo {

    public String echo(String msg) {
        return msg + " " + msg + " " + msg;
    }

}
