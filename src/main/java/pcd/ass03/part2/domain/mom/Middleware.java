package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Middleware {
    private final Connection connection;
    public Middleware() throws URISyntaxException, NoSuchAlgorithmException, IOException, KeyManagementException, TimeoutException {
            this.connection = RemoteBroker.createConnection();
    }

    public void sendMessage(final String message){

    }
}
