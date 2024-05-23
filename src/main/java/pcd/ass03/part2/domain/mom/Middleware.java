package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.logics.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Middleware extends Controller {
    private final Connection connection;
    private final Channel channel;
    private final DeliverCallback deliverCallback;
    private static final String EXHANGE_NAME = "game";
    private static final String ROUTING_KEY = "move";
    private static final String TYPE = "direct";

    public Middleware(final Difficulty difficulty) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        super(difficulty);
        this.connection = RemoteBroker.createConnection();
        this.channel = this.connection.createChannel();
        this.setChannel();
        this.deliverCallback = (consumerTag, delivery) -> {
            final String message = new String(delivery.getBody(), "UTF-8");
            final Move move = Message.unmarshall(message);
            super.getCurrentBoard().putValue(move.cell(), move.value());
        };
        final String queueName = channel.queueDeclare().getQueue();
        this.channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private void setChannel(){
        try {
            this.channel.exchangeDeclare(EXHANGE_NAME, TYPE);
            String queueName = channel.queueDeclare().getQueue();
            this.channel.queueBind(queueName, EXHANGE_NAME, TYPE);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void marshall(final String message) throws IOException {
        this.channel.basicPublish(EXHANGE_NAME, ROUTING_KEY, null, message.getBytes());
    }
}
