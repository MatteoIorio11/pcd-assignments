package pcd.ass02.executors.model.simtrafficbase;

import pcd.ass02.executors.model.simengineseq.AbstractAgent;
import pcd.ass02.executors.model.simengineseq.AgentSynchronizer;

public class AgentTask implements Runnable {
    private final int dt;
    private final AbstractAgent agent;
    private final AgentSynchronizer synchronizer = AgentSynchronizer.getInstance();

    public AgentTask(final AbstractAgent agent, final int dt) {
        this.agent = agent;
        this.dt = dt;
    }

    @Override
    public void run() {
        this.agent.senseStep();
        this.agent.decideStep(this.dt);
        this.synchronizer.executeCriticalSection(this.agent::actStep);
//        System.out.println("[" + Thread.currentThread() + " || " + this.agent.getId() + "]: " + "Finished");
    }
}
