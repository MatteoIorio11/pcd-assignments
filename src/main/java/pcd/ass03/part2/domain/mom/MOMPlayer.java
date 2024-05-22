package pcd.ass03.part2.domain.mom;

import pcd.ass03.part2.domain.Board;

import java.util.Objects;

public class MOMPlayer {
    final Board board;

    public MOMPlayer(final Board board){
        this.board = Objects.requireNonNull(board);
    }
}
