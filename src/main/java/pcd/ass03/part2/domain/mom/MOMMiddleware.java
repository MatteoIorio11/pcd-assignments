package pcd.ass03.part2.domain.mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import pcd.ass03.part2.domain.*;
import pcd.ass03.part2.logics.Controller;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MOMMiddleware extends Controller {
    private Optional<Channel> channel = Optional.empty();
    private static final String EXCHANGE_NAME = "game";
    private static final String QUEUE_NAME = "game";
    private static final String ROUTING_KEY = "move";

    private static final String TYPE = "direct";

    public MOMMiddleware(final Difficulty difficulty) {
        super(difficulty);
        Optional<Connection> connection = Objects.requireNonNull(RemoteBroker.createConnection());
        connection.ifPresent(conn -> {
            try {
                this.channel = Optional.of(conn.createChannel());
            }catch (Exception e){
                //
            }
        });
        this.setChannel();
        this.setCallback();
        try {
            this.marshall(Message.marshallBoard(super.getCurrentBoard()));
        }catch (Exception e){
            //
        }
    }

    private void setCallback(){
        try {
            final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                final String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Incoming message" + message);
                this.unmarshall(message);
            };
            this.channel.ifPresent(ch -> {
                try {
                    ch.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
                    });
                } catch (Exception e) {
                    //
                }
            });
        }catch (Exception e){
            //
        }
    }

    private void setChannel(){
        this.channel.ifPresent(ch -> {
            try {
                ch.exchangeDeclare(EXCHANGE_NAME, TYPE);
                ch.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            }catch (Exception e){
                //
            }
        });
    }

    public void marshall(final String message) {
        this.channel.ifPresent(ch -> {
            try {
                ch.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
            }catch (Exception e){
                //
            }
        });
    }

    @Override
    public boolean putValue(final Cell cell, final int value) {
        try {
            System.out.println("AOO");
            this.sudokuBoard.putValue(cell, value);
            this.marshall(Message.marshallBoard(super.getCurrentBoard()));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void unmarshall(final String message){
        try{
            final Board board = Message.unmarshallBoard(message);
            super.sudokuBoard = Objects.requireNonNull(board);
        }catch (Exception e){
            //
        }
    }
}
