package pcd.ass03.part2.domain.rmi;

import pcd.ass03.part2.domain.Cell;
import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.logics.Controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
        } catch (final NotBoundException e) {
            this.registry = LocateRegistry.getRegistry();
            final RemoteBoard remoteBoard = RemoteBoardImpl.fromBoard(super.sudokuBoard);
//            registry.rebind(REMOTE_BOARD_NAME, remoteBoardStub);
            this.remoteBoardStub = (RemoteBoard) UnicastRemoteObject.exportObject(remoteBoard, 0);
        }
    }

    @Override
    public boolean putValue(Cell cell, int value) {
        try {
            final boolean res = remoteBoardStub.putValue(cell, value);
            super.sudokuBoard = remoteBoardStub.getBoard();
            return res;
        } catch (final RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try{
            new RMIMiddleware(Difficulty.DEBUG);
        }catch (Exception e){
            //
        }
    }
}
