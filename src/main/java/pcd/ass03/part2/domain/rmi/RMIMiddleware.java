package pcd.ass03.part2.domain.rmi;

import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.logics.Controller;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;

public class RMIMiddleware extends Controller {
    private Optional<Registry> registry = Optional.empty();
    private static final String HOST = "localhost";
    public RMIMiddleware(final Difficulty difficulty) {
        super(difficulty);
        try {
            this.registry = Optional.of(LocateRegistry.getRegistry(HOST));
        }catch (Exception e){
            //
        }
    }
}
