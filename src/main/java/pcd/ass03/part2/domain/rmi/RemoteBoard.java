package pcd.ass03.part2.domain.rmi;

import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.BoardGenerator;
import pcd.ass03.part2.domain.Cell;
import pcd.ass03.part2.domain.Difficulty;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class RemoteBoard implements Remote {

    private final Board board;

    public RemoteBoard(final Difficulty difficulty) {
        this.board = BoardGenerator.initializeBoard(new Board(), difficulty);
    }

    private RemoteBoard(final Board board) {
        this.board = board;
    }

    public boolean putValue(final Cell cell, final int value) throws RemoteException{
        return this.board.putValue(cell, value);
    }

    public void removeValue(final Cell cell) throws RemoteException {
        this.board.removeValue(cell);
    }

    public Board getBoard() throws RemoteException{
        return this.board;
    }

    public static RemoteBoard fromBoard(final Board board) throws RemoteException{
        return new RemoteBoard(board);
    }

}
