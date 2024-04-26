package pcd.ass02.server.controller;

import io.vertx.core.*;
import pcd.ass02.server.model.lib.CounterModality;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.event.html.EventLoopCounter;
import pcd.ass02.server.model.lib.factory.WordCounterFactory;
import pcd.ass02.server.model.lib.response.Response;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Controller {

    private record CurrentAlgorithm<E>(WordOccurrence<E> algorithm){
        public static CurrentAlgorithm<Response> from(final WordOccurrence<Response> inputAlgorithm){
            return new CurrentAlgorithm<>(inputAlgorithm);
        }

        public static CurrentAlgorithm<Future<Response>> fromFuture(final EventLoopCounter inputAlgorithm){
            return new CurrentAlgorithm<>(inputAlgorithm);
        }

        public void stop(){
            this.algorithm.stopProcess();
        }
    }

    private Optional<CurrentAlgorithm<Response>> optResponseAlgorithm = Optional.empty();
    private Optional<CurrentAlgorithm<Future<Response>>> optFutureAlgorithm = Optional.empty();

    public List<CounterModality> getAlgorithms(){
        return Arrays.stream(CounterModality.values()).toList();
    }

    public CompletableFuture<Future<Response>> startSearch(final String url, final int depth, final String word, final CounterModality algorith) throws ExecutionException, InterruptedException {
        if(Objects.nonNull(url) &&
        Objects.nonNull(word) &&
        Objects.nonNull(algorith) &&
        depth >= 1 &&
        this.optFutureAlgorithm.isEmpty() && this.optResponseAlgorithm.isEmpty()){
            return CompletableFuture.supplyAsync(() -> switch (algorith)  {
                case EVENT -> this.startEventLoop(url, depth, word);
                case REACTIVE -> this.startReactive(url, depth, word);
                case VIRTUAL -> this.startVirtual(url, depth, word);
            });

        }else{
            System.out.println(this.optFutureAlgorithm);
            System.out.println(this.optResponseAlgorithm);
            throw new IllegalArgumentException("One of the input argument is not correct");
        }
    }

    private Future<Response> startVirtual(final String url, final int depth, final String word) {
        final var algorithm = WordCounterFactory.fromVirtual();
        this.optResponseAlgorithm = Optional.of(CurrentAlgorithm.from(algorithm));
        final Promise<Response> promise = Promise.promise();
        promise.complete(algorithm.getWordOccurrences(url, word, depth));
        return promise.future();
    }

    private Future<Response> startReactive(final String url, final int depth, final String word) {
        final var algorithm = WordCounterFactory.fromReactive();
        this.optResponseAlgorithm = Optional.of(CurrentAlgorithm.from(algorithm));
        final Promise<Response> promise = Promise.promise();
        promise.complete(algorithm.getWordOccurrences(url, word, depth));
        return promise.future();
    }

    private Future<Response> startEventLoop(final String url, final int depth, final String word){
        final EventLoopCounter algorithm = WordCounterFactory.fromLoopCounter();
        this.optFutureAlgorithm = Optional.of(CurrentAlgorithm.fromFuture(algorithm));
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(algorithm);
        return algorithm.getWordOccurrences(url, word, depth);
    }

    public Optional<Response> getPartialResult(){
        if(this.optResponseAlgorithm.isPresent()){
            return this.optResponseAlgorithm.get().algorithm().partialResult();
        }else if(this.optFutureAlgorithm.isPresent()) {
            return this.optFutureAlgorithm.get().algorithm().partialResult();
        }
        return Optional.empty();
    }

    public void stop(){
        this.optResponseAlgorithm.ifPresent(CurrentAlgorithm::stop);
        this.optFutureAlgorithm.ifPresent(CurrentAlgorithm::stop);
        this.optFutureAlgorithm = Optional.empty();
        this.optResponseAlgorithm = Optional.empty();
    }
}
