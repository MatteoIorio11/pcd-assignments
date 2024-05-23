package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import pcd.ass03.part2.domain.Cell;
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
    private static final String EXCHANGE_NAME = "game";
    private static final String QUEUE_NAME = "game"
    private static final String ROUTING_KEY = "move";

    private static final String TYPE = "direct";

    public Middleware(final Difficulty difficulty) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        super(difficulty);
        this.connection = RemoteBroker.createConnection();
        this.channel = this.connection.createChannel();
        this.setChannel();
        this.setCallback();

    }

    private void setCallback(){
        try {
            final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                final String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Incoming message" + message);
            };
            this.channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            //
        }
    }

    private void setChannel(){
        try {
            this.channel.exchangeDeclare(EXCHANGE_NAME, TYPE);
            this.channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void marshall(final String message) throws IOException {
        this.channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
    }

    @Override
    public boolean putValue(Cell cell, int value) {
        return super.putValue(cell, value);
    }
    

    public static void main(String[] args) {
        try {
            final var m = new Middleware(Difficulty.DEBUG);
            final var z = new Middleware(Difficulty.DEBUG);
            m.marshall("ciao");

        }catch (Exception e){
            //
        }
    }
}
