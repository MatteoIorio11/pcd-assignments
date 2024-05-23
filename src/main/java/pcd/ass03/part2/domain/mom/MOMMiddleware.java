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
    }

    private void setCallback(){
        try {
            final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                final String message = new String(delivery.getBody(), "UTF-8");
                this.unmarshall(message);
                System.out.println("Incoming message" + message);
            };
            this.channel.ifPresent(ch -> {
                try {
                    ch.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
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
    public boolean putValue(Cell cell, int value) {
        try {
            final boolean result = super.putValue(cell, value);
            this.marshall(Message.marshall(super.getCurrentBoard().getCells()));
            return result;
        }catch (Exception e){
            return false;
        }
    }

    private void unmarshall(final String message){
        try{
            final Map<Cell, Integer> board = Message.unmarshallBoard(message);
            board.forEach(this::putValue);
        }catch (Exception e){
            //
        }
    }

    public static void main(String[] args) {
        try {
            SudokuSolver.solve(new Board())
                    .ifPresent(b -> {
                        try {
                            final var m = new MOMMiddleware(Difficulty.DEBUG);
                            final var z = new MOMMiddleware(Difficulty.DEBUG);
                            System.out.println("Sending: " + b.getCells());
                            m.marshall(Message.marshall(b.getCells()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        }catch (Exception e){
            //
        }
    }
}
