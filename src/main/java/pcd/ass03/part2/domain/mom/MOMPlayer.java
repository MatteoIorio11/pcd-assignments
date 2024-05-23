package pcd.ass03.part2.domain.mom;

import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class MOMPlayer {
    private final Board board;
    private final Middleware middleware;
    public MOMPlayer(final Board board) throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
        this.board = Objects.requireNonNull(board);
        this.middleware = new Middleware();
    }

    public void putValue(final Cell cell, final int value){
        this.board.putValue(cell, value);

    }

    public void removeValue(final Cell cell){
        this.removeValue(cell);
    }
}
