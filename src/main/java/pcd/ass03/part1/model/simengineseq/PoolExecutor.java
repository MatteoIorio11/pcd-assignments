package pcd.ass03.part1.model.simengineseq;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class PoolExecutor {
    private final static int MAX_TIMEOUT = 10;
    private final static TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private PoolExecutor(){}

    public static <E> void executePool(final List<E> agents, Consumer<E> task){
        try(final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1)){
            executorService.execute(() -> {
                agents.forEach(task);
            });
            executorService.shutdown();
            final var result = executorService.awaitTermination(PoolExecutor.MAX_TIMEOUT, PoolExecutor.TIME_UNIT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
