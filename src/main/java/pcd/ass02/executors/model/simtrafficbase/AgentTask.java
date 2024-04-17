package pcd.ass02.executors.model.simtrafficbase;

import pcd.ass02.executors.model.simengineseq.AbstractAgent;
import pcd.ass02.executors.model.simengineseq.AgentSynchronizer;

import java.util.concurrent.Callable;

public class AgentTask implements Callable<Boolean> {
    private final int dt;
    private final AbstractAgent agent;
    private final AgentSynchronizer synchronizer = AgentSynchronizer.getInstance();

    public AgentTask(final AbstractAgent agent, final int dt) {
        this.agent = agent;
        this.dt = dt;
    }

    @Override
    public Boolean call() {
        this.agent.senseStep();
        this.agent.decideStep(this.dt);
        this.synchronizer.executeCriticalSection(this.agent::actStep);
        return true;
    }
}
