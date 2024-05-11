package pcd.ass02.part1.model.simtrafficbase;

import pcd.ass02.part1.model.simengineseq.AbstractAgent;
import pcd.ass02.part1.model.simengineseq.AgentSynchronizer;

import java.util.concurrent.Callable;

public class AgentTask {
    private final int dt;
    private final AbstractAgent agent;
    private final AgentSynchronizer synchronizer = AgentSynchronizer.getInstance();

    public AgentTask(final AbstractAgent agent, final int dt) {
        this.agent = agent;
        this.dt = dt;
    }

    public Callable<Void> performSenseAndDecideSteps() {
        return () -> {
            this.agent.senseStep();
            this.agent.decideStep(this.dt);
            return null;
        };
    }

    public Callable<Boolean> performActStep() {
        return () -> {
            this.synchronizer.executeCriticalSection(this.agent::actStep);
            return null;
        };
    }
}
