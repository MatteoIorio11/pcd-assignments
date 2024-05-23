package pcd.ass03.part2.domain.rmi;

import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.logics.Controller;

public class RMIMiddleware extends Controller {
    public RMIMiddleware(final Difficulty difficulty) {
        super(difficulty);
    }
}
