package pcd.ass03.part2.domain.rmi;

import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteBoard extends Remote {

    boolean putValue(Cell cell, int value) throws RemoteException;

    void removeValue(Cell cell) throws RemoteException;

    Board getBoard() throws RemoteException;
}
