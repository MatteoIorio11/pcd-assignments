package pcd.ass03.part2.domain.rmi;

import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;
import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.logics.Controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * Client
 */
public class RMIMiddleware extends Controller {
    private Registry registry;

    private static final String HOST = "localhost";
    private static final String REMOTE_BOARD_NAME = "remoteBoard";

    private RemoteBoard remoteBoardStub;

    public RMIMiddleware(final Difficulty difficulty) throws RemoteException {
        super(difficulty);
        try {
            this.registry = LocateRegistry.getRegistry(HOST);
            this.remoteBoardStub = (RemoteBoard) this.registry.lookup(REMOTE_BOARD_NAME);
            System.out.println(Arrays.toString(this.registry.list()));
            super.sudokuBoard = this.remoteBoardStub.getBoard();
        } catch (final NotBoundException e) {
            this.registry = LocateRegistry.getRegistry(HOST, 0);
            final RemoteBoard remoteBoard = RemoteBoardImpl.fromBoard(super.sudokuBoard);
            this.remoteBoardStub = (RemoteBoard) UnicastRemoteObject.exportObject(remoteBoard, 0);
            registry.rebind(REMOTE_BOARD_NAME, remoteBoardStub);
        }
    }

    @Override
    public boolean putValue(Cell cell, int value) {
        try {
            final boolean res = remoteBoardStub.putValue(cell, value);
            super.sudokuBoard = remoteBoardStub.getBoard();
            return res;
        } catch (final Exception e) {
            try {
                this.registry.unbind(REMOTE_BOARD_NAME);
            }catch (Exception z){
                //
            }
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Board getCurrentBoard() {
        try {
            super.sudokuBoard = this.remoteBoardStub.getBoard();
            return super.sudokuBoard;
        }catch (RemoteException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try{
            final var x = new RMIMiddleware(Difficulty.DEBUG);
            x.putValue(new Cell(0, 0), 1);
        }catch (Exception e){
            //
        }
    }
}
