package pcd.ass02.server.model;

import io.vertx.core.Vertx;
import pcd.ass02.server.model.lib.factory.WordCounterFactory;
import pcd.ass02.server.model.lib.html.Page;
import pcd.ass02.server.model.lib.virtual.html.VirtualCounter;

import java.util.Optional;

public class main {

    public static void main(String[] args) {
        /*
        Some urls:
            - https://en.wikipedia.org/wiki/Main_Page
            - https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
            - https://wikileaks.org
            - https://www.tensorflow.org/?hl=it
         */
        // EventLoop Test
        final var elvc = WordCounterFactory.fromLoopCounter();
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(elvc);
        elvc.getWordOccurrences("https://en.wikipedia.org/wiki/Main_Page", "the", 2)
                .onSuccess(res -> {
                    System.out.println(res.count());
                    vertx.close();
                });
    }
}
