package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class RemoteBroker {
    final ConnectionFactory factory;
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
