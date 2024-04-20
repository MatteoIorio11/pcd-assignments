package pcd.ass02.server.controller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import pcd.ass02.server.model.lib.CounterModality;
import pcd.ass02.server.model.lib.event.html.EventLoopCounter;
import pcd.ass02.server.model.lib.factory.WordCounterFactory;
import pcd.ass02.server.model.lib.response.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Controller {



    public List<CounterModality> getAlgorithms(){
        return Arrays.stream(CounterModality.values()).toList();
    }

    public Map<String, Long> startSearch(final String url, final int depth, final String word, final CounterModality algorith){
        if(Objects.nonNull(url) &&
        Objects.nonNull(word) &&
        Objects.nonNull(algorith) &&
        depth >= 0){
            return switch (algorith)  {
                case EVENT -> this.startEventLoop(url, depth, word).count();
                case REACTIVE -> this.startReactive(url, depth, word).count();
                case VIRTUAL -> this.startVirtual(url, depth, word).count();
            };
        }else{
            throw new IllegalArgumentException("One of the input argument is not correct");
        }
    }

    private Response startVirtual(final String url, final int depth, final String word) {
        final var algorithm = WordCounterFactory.fromVirtual();
        return algorithm.getWordOccurrences(url, word, depth);
    }

    private Response startReactive(final String url, final int depth, final String word) {
        return null;
    }

    private Response startEventLoop(final String url, final int depth, final String word){
        final EventLoopCounter algorithm = WordCounterFactory.fromLoopCounter();
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(algorithm);
        algorithm.getWordOccurrences(url, word, depth);
        return null;
    }
}
