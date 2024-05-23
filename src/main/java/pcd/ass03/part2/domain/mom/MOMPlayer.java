package pcd.ass03.part2.domain.mom;

import com.fasterxml.jackson.core.JsonProcessingException;
import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;
import pcd.ass03.part2.domain.Difficulty;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class MOMPlayer {
    private final Board board;
    private final Middleware middleware;
    public MOMPlayer(final Board board, final Difficulty difficulty) throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
        this.board = Objects.requireNonNull(board);
        this.middleware = new Middleware(difficulty);
    }

    public void putValue(final Cell cell, final int value) throws IOException {
        this.board.putValue(cell, value);
        this.middleware.marshall(Message.marshall(cell, value));
    }

    public void removeValue(final Cell cell){
        this.removeValue(cell);
    }
}
