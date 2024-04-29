package pcd.ass02.server.model;

import io.vertx.core.Vertx;
import pcd.ass02.server.model.lib.strategy.factory.WordCounterFactory;

public class CounterMain {

    public static void main(String[] args) {
        /*
        Some urls:
            - https://en.wikipedia.org/wiki/Main_Page
            - https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
            - https://wikileaks.org
            - https://www.tensorflow.org/?hl=it
         */
        // EventLoop Test
        System.out.println("[TEST] Start");
        final long startingTime = System.nanoTime();
        final var elvc = WordCounterFactory.fromLoopCounter();
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(elvc);
        elvc.getWordOccurrences("https://en.wikipedia.org/wiki/Main_Page", "the", 3)
                .onSuccess(res -> {
                    res.count()
                            .forEach((k, v) -> System.out.println("Page: " + k + " Count : " + v));
                    vertx.close();
                    System.out.println("[TEST] End, elapsed time: " + Math.abs(System.nanoTime() - startingTime) + " ns");
                });
    }
}
