package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Middleware {
    private final Channel channel;
    public Middleware() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this.channel = RemoteBroker.createChannel();
        
    }

    public void sendMessage(final String message){

    }
}
