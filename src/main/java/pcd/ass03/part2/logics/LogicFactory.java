package pcd.ass03.part2.logics;

import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.domain.mom.Middleware;

import java.util.Optional;

public class LogicFactory {

    public static Optional<Controller> getMomLogic(final Difficulty difficulty) {
        try {
            return Optional.of(new Middleware(difficulty));
        } catch (final Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
