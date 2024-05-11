package pcd.ass02.part2.model;

import io.vertx.core.Vertx;
import pcd.ass02.part2.model.lib.strategy.factory.WordCounterFactory;

public class CounterMain {

    public static String SERVER_LINK = "http://localhost:8000/Massimo_Boldi.html";
    public static String WORD = "the";
    public static int DEPTH = 3;

    public static void main(String[] args) {
        /*
        Some urls:
            - https://en.wikipedia.org/wiki/Main_Page
            - https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
            - https://wikileaks.org
            - https://www.tensorflow.org/?hl=it
         */
        startVirtualThreadTest();
//        startReactiveTest();
//        startVertxTest();
    }

    private static void startVertxTest() {
        final var elvc = WordCounterFactory.fromLoopCounter();
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(elvc);
        final long startingTime = System.currentTimeMillis();
        elvc.getWordOccurrences(SERVER_LINK, WORD, DEPTH)
                .onSuccess(res -> {
                    final var endTime = Math.abs(System.currentTimeMillis() - startingTime);
                    final var count = res.count();
                    count.forEach((k, v) -> System.out.println("Page: " + k + " Count : " + v));
                    vertx.close();
                    System.out.println("[TEST - Vertx] End, elapsed time: " + endTime + " ms");
                });
    }

    private static void startVirtualThreadTest() {
        final var virtWc = WordCounterFactory.fromVirtual();
        final long startTime = System.currentTimeMillis();
        final var res = virtWc.getWordOccurrences(SERVER_LINK, WORD, DEPTH);
        final var endTime = Math.abs(System.currentTimeMillis() - startTime);
        final var count = res.count();
        count.forEach((k, v) -> System.out.println("Page: " + k + " Count : " + v));
        System.out.println("[TEST - Virtual] End, elapsed time: " + endTime + " ms");
    }

    private static void startReactiveTest() {
        final var reactWc = WordCounterFactory.fromReactive();
        final long startTime = System.currentTimeMillis();
        final var res = reactWc.getWordOccurrences(SERVER_LINK, WORD, DEPTH);
        final var endTime = Math.abs(System.currentTimeMillis() - startTime);
        final var count = res.count();
        count.forEach((k, v) -> System.out.println("Page: " + k + " Count : " + v));
        System.out.println("[TEST - Reactive] End, elapsed time: " + endTime + " ms");
    }
}
