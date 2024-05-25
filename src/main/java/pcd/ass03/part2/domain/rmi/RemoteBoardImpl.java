package pcd.ass03.part2.domain.rmi;

import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.BoardGenerator;
import pcd.ass03.part2.domain.Cell;
import pcd.ass03.part2.domain.Difficulty;

import java.rmi.RemoteException;

public class RemoteBoardImpl implements RemoteBoard {

    private final Board board;

    public RemoteBoardImpl(final Difficulty difficulty) {
        this.board = BoardGenerator.initializeBoard(new Board(), difficulty);
    }

    private RemoteBoardImpl(final Board board) {
        this.board = board;
    }

    @Override
    public boolean putValue(final Cell cell, final int value) throws RemoteException{
        return this.board.putValue(cell, value);
    }

    @Override
    public void removeValue(final Cell cell) throws RemoteException {
        this.board.removeValue(cell);
    }

    @Override
    public Board getBoard() throws RemoteException{
        return this.board;
    }

    public static RemoteBoard fromBoard(final Board board) {
        return new RemoteBoardImpl(board);
    }
}
