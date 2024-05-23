package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import pcd.ass03.part2.domain.Board;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Middleware {
    private final Connection connection;
    private final Channel channel;
    private final DeliverCallback deliverCallback;
    private static final String EXHANGE_NAME = "game";
    private static final String ROUTING_KEY = "move";
    private static final String TYPE = "direct";
    private final Queue<Move> incomingMoves;

    public Middleware(final BiConsumer<Board, Move> onReceive) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this.connection = RemoteBroker.createConnection();
        this.channel = this.connection.createChannel();
        this.incomingMoves = new LinkedList<>();
        this.setChannel();
        this.deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            incomingMoves.add(Message.getMove(message));
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

        };
        String queueName = channel.queueDeclare().getQueue();
        this.channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private void setChannel(){
        try {
            this.channel.exchangeDeclare(EXHANGE_NAME, TYPE);
            String queueName = channel.queueDeclare().getQueue();
            this.channel.queueBind(queueName, EXHANGE_NAME, TYPE);
        }catch (Exception ex){
            //
        }
    }

    public void sendMessage(final String message) throws IOException, TimeoutException {
        try {
            this.channel.basicPublish(EXHANGE_NAME, TYPE, null, message.getBytes());
        }catch (Exception ex){
            //
        }
    }
}
