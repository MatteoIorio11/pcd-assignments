package pcd.ass02.server.controller;

import io.vertx.core.*;
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

    public Future<Response> startSearch(final String url, final int depth, final String word, final CounterModality algorith){
        if(Objects.nonNull(url) &&
        Objects.nonNull(word) &&
        Objects.nonNull(algorith) &&
        depth >= 0){
            return switch (algorith)  {
                case EVENT -> this.startEventLoop(url, depth, word);
                case REACTIVE -> this.startReactive(url, depth, word);
                case VIRTUAL -> this.startVirtual(url, depth, word);
            };
        }else{
            throw new IllegalArgumentException("One of the input argument is not correct");
        }
    }

    private Future<Response> startVirtual(final String url, final int depth, final String word) {
        final var algorithm = WordCounterFactory.fromVirtual();
        final Promise<Response> promise = Promise.promise();
        promise.complete(algorithm.getWordOccurrences(url, word, depth));
        return promise.future();
    }

    private Future<Response> startReactive(final String url, final int depth, final String word) {
        return null;
    }

    private Future<Response> startEventLoop(final String url, final int depth, final String word){
        final EventLoopCounter algorithm = WordCounterFactory.fromLoopCounter();
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(algorithm);
        return algorithm.getWordOccurrences(url, word, depth);
    }
}
