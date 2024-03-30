package pcd.ass01.simengineseq;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AgentExecutor {
    private static final List<Thread> threadAgents = new ArrayList<>();
    private static int batchSize = Runtime.getRuntime().availableProcessors();

    private AgentExecutor(){

    }


    public static void executeAgents(final List<AbstractAgent> agents){
        batchSize = Math.min(agents.size(), batchSize);
        agents.forEach(agent -> {
            threadAgents.add(new Thread(agent));
            if((threadAgents.size() ^ batchSize) == 0){
                AgentExecutor.processPool();
                threadAgents.clear();
            }
        });
    }

    public static void executeFilteredAgents(final List<AbstractAgent> agents,
                                             Consumer<AbstractAgent> consumer){
        agents.forEach(agent -> {
            threadAgents.add(new Thread(() -> consumer.accept(agent)));
            if((threadAgents.size() ^ batchSize) == 0){
                AgentExecutor.processPool();
                threadAgents.clear();
            }
        });
    }
    private static void joinPool(){
        threadAgents.forEach(th -> {
            try{
                th.join();
            }catch (InterruptedException e) {
                System.err.println("[Process Pool] -> Error during the join: " + e.getMessage());
            }
        });
    }

    private static void processPool(){
        threadAgents.forEach(Thread::start);
        AgentExecutor.joinPool();
    }
}
