package pcd.ass03.part2.domain.mom;

import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;

import java.util.Objects;

public class MOMPlayer {
    final Board board;
    public MOMPlayer(final Board board){
        this.board = Objects.requireNonNull(board);
    }

    public void putValue(final Cell cell, final int value){
        this.board.putValue(cell, value);
    }

    public void removeValue(final Cell cell){
        this.removeValue(cell);
    }
}
