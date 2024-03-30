package pcd.ass01.simengineseq;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AgentExecutor {
    private static final List<Thread> threadAgents = new ArrayList<>();
    private AgentExecutor(){

    }

    private static int batchSize = Runtime.getRuntime().availableProcessors();

    public static void executeAgents(final List<AbstractAgent> agents){
        batchSize = Math.min(agents.size(), batchSize);
        agents.forEach(agent -> {
            threadAgents.add(new Thread(agent));
            threadAgents.getLast().start();
            if((threadAgents.size()  ^ batchSize) == 0){
                System.out.println("Pool created");
                //AgentExecutor.processPool(threadAgents);
                AgentExecutor.joinPool();
                threadAgents.clear();
            }
        });
    }

    public static void executeFilteredAgents(final List<AbstractAgent> agents,
                                             Consumer<AbstractAgent> consumer){
        batchSize = Math.min(agents.size(), batchSize);
        System.err.println(agents.size());
        System.exit(1);
        agents.forEach(agent -> {
            threadAgents.add(new Thread(agent::act));
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
        System.out.println("Pool joined");
    }

    private static void processPool(){
        threadAgents.forEach(Thread::start);
        AgentExecutor.joinPool();
    }
}
