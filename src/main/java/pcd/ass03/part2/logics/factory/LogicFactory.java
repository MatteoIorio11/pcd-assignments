package pcd.ass03.part2.logics.factory;

import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.domain.mom.MOMMiddleware;
import pcd.ass03.part2.domain.rmi.RMIMiddleware;
import pcd.ass03.part2.logics.Controller;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public class LogicFactory {

    public enum Logics {
        RMI("RMI"),
        MOM("MOM");

        private final String name;

        Logics(final String name) {
            this.name = name;
        }

        public String getName() { return this.name; }

        public static Logics fromClassName(final String c) {
            if (c.contains("RMI")) {
                return RMI;
            } else if (c.contains("MOM")) {
                return MOM;
            } else {
                throw new IllegalStateException("Given class is not found: " + c);
            }
        }
    }

    public static Optional<Controller> createMomLogic(final Difficulty difficulty, final Optional<String> host) {
        try {
            return Optional.of(new MOMMiddleware(difficulty, host));
        } catch (final Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Controller> createRmiLogic(final Difficulty difficulty, final Optional<String> host) {
        try {
            return Optional.of(new RMIMiddleware(difficulty, host));
        } catch (final RemoteException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static List<Class<? extends Controller>> getLogicClasses() {
        return List.of(MOMMiddleware.class, RMIMiddleware.class);
    }

    @SuppressWarnings("unchecked")
    public static Optional<Controller> getLogicInstance(final Logics logics, final Difficulty difficulty, final Optional<String> host) {
        return (Optional<Controller>) LogicFactory.getLogicClasses().stream().filter(c -> c.getName().contains(logics.getName())).map(c -> {
            try {
                System.out.println(host);
                return c.getConstructors()[0].newInstance(difficulty, host);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).findFirst();
    }

    public static void main(String[] args) throws RemoteException {
        new RMIMiddleware(Difficulty.EASY, Optional.of("localhost"));
    }
}
