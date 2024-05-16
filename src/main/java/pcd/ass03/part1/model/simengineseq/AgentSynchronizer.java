package pcd.ass03.part1.model.simengineseq;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AgentSynchronizer {
    private final Lock lock;
    private static AgentSynchronizer agentSynchronizer;

    private AgentSynchronizer(){
        this.lock = new ReentrantLock();
    }

    public static AgentSynchronizer getInstance(){
        if(Objects.isNull(agentSynchronizer)){
            agentSynchronizer = new AgentSynchronizer();
        }
        return agentSynchronizer;
    }

    public void executeCriticalSection(final Runnable action) {
        this.lock.lock();
        action.run();
        this.lock.unlock();
    }
}