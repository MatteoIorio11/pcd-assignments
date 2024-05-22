package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class RemoteBroker {
    private final ConnectionFactory factory;
    private static final String QUEUE_NAME= "game";
    private static final String HOST = "rogmlmgd";
    private static final String VHOST = "rogmlmgd";
    private static final String PASSWORD = "sjdLZ_RORmQtT6VhpjzhiYa3pVIcOgqq";
    private static final String SERVER = "rat.rmq2.cloudamqp.com";
    private static final int PORT = 5672;
    private static final String URI = "amqp://"+RemoteBroker.HOST+":"+RemoteBroker.PASSWORD+"@"+RemoteBroker.SERVER+":"+RemoteBroker.PORT+"/"+ RemoteBroker.VHOST;
    public RemoteBroker() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this.factory = new ConnectionFactory();
        this.factory.setHost("rogmlmgd");
        this.factory.setVirtualHost("rogmlmgd");
        this.factory.setPassword("sjdLZ_RORmQtT6VhpjzhiYa3pVIcOgqq");
        this.factory.setPort(5672);
        this.factory.setUri("amqp://rogmlmgd:sjdLZ_RORmQtT6VhpjzhiYa3pVIcOgqq@rat.rmq2.cloudamqp.com:5672/rogmlmgd");
        this.factory.setConnectionTimeout(30000);
    }

}
