package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AgentSynchronizer;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class AgentPoolWorker implements Runnable {
    private final int nAgents;
    private final List<AbstractAgent> agents;
    private final AgentSynchronizer synchronizer;
    private int dt = 0;

    public AgentPoolWorker(
            final List<AbstractAgent> agents,
            final AgentSynchronizer synchronizer
            ) {
        this.agents = agents;
        this.nAgents = this.agents.size();
        this.synchronizer = synchronizer;
    }

    @Override
    public void run() {
        this.agents.forEach(AbstractAgent::senseStep);
        this.agents.forEach(a -> a.decideStep(this.dt));
        try {
            this.synchronizer.awaitBarrier();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        this.agents.forEach(agent -> {
            this.synchronizer.executeCriticalSection(agent::actStep);
        });
    }

    public void setDt(final int dt) {
        this.dt = dt;
    }
}
