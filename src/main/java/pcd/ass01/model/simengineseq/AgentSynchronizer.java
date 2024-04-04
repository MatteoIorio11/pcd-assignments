package pcd.ass01.model.simengineseq;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AgentSynchronizer {
    private final Lock lock;
    private final CyclicBarrier agentBarrier;
    private static AgentSynchronizer agentSynchronizer;

    private AgentSynchronizer(final int nWorkers){
        this.lock = new ReentrantLock();
        this.agentBarrier = new CyclicBarrier(nWorkers);
    }

    public static AgentSynchronizer getInstance(final int nWorkers){
        if(Objects.isNull(agentSynchronizer)){
            agentSynchronizer = new AgentSynchronizer(nWorkers);
        }
        return agentSynchronizer;
    }

    public void awaitBarrier() throws BrokenBarrierException, InterruptedException {
        this.agentBarrier.await();
    }

    public void executeCriticalSection(final Runnable action) {
        this.lock.lock();
        action.run();
        this.lock.unlock();
    }
}
