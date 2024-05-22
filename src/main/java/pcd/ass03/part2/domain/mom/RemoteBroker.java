package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RemoteBroker {
    private static final ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory();
    private static final String HOST = "rogmlmgd";
    private static final String VHOST = "rogmlmgd";
    private static final String PASSWORD = "sjdLZ_RORmQtT6VhpjzhiYa3pVIcOgqq";
    private static final String SERVER = "rat.rmq2.cloudamqp.com";
    private static final int PORT = 5672;
    private static final String URI = "amqp://"+RemoteBroker.HOST+":"+RemoteBroker.PASSWORD+"@"+RemoteBroker.SERVER+":"+RemoteBroker.PORT+"/"+ RemoteBroker.VHOST;


    private RemoteBroker(){}
    private static void setConnectionInformation() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        CONNECTION_FACTORY.setHost(RemoteBroker.HOST);
        CONNECTION_FACTORY.setVirtualHost(RemoteBroker.VHOST);
        CONNECTION_FACTORY.setPassword(RemoteBroker.PASSWORD);
        CONNECTION_FACTORY.setPort(RemoteBroker.PORT);
        CONNECTION_FACTORY.setUri(RemoteBroker.URI);
        CONNECTION_FACTORY.setConnectionTimeout(30000);
    }

    public static Connection createConnection() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        setConnectionInformation();
        return CONNECTION_FACTORY.newConnection();
    }
}
