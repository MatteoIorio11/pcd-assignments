package pcd.ass03.part2.logics.factory;

import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.domain.mom.MOMMiddleware;
import pcd.ass03.part2.logics.Controller;

import java.lang.reflect.InvocationTargetException;
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

    public static Optional<Controller> createMomLogic(final Difficulty difficulty) {
        try {
            return Optional.of(new MOMMiddleware(difficulty));
        } catch (final Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Controller> createRmiLogic(final Difficulty difficulty) {
        return Optional.of(new pcd.ass03.part2.domain.rmi.RMIMiddleware(difficulty));
    }

    public static List<Class<? extends Controller>> getLogicClasses() {
        return List.of(MOMMiddleware.class, pcd.ass03.part2.domain.rmi.RMIMiddleware.class);
    }

    @SuppressWarnings("unchecked")
    public static Optional<Controller> getLogicInstance(final Logics logics, final Difficulty difficulty) {
        return (Optional<Controller>) LogicFactory.getLogicClasses().stream().filter(c -> c.getName().contains(logics.getName())).map(c -> {
            try {
                return c.getConstructors()[0].newInstance(difficulty);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).findFirst();
    }
}
