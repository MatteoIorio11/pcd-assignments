package pcd.ass02.server.model.lib.factory;

import io.vertx.core.AbstractVerticle;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.event.fs.EventLoopCounterFS;
import pcd.ass02.server.model.lib.event.html.EventLoopCounter;
import pcd.ass02.server.model.lib.reactive.ReactiveCounter;
import pcd.ass02.server.model.lib.response.Response;
import pcd.ass02.server.model.lib.virtual.fs.VirtualCounterFS;
import pcd.ass02.server.model.lib.virtual.html.VirtualCounter;

public class WordCounterFactory {

    private WordCounterFactory(){}

    public static AbstractVerticle fromLoopCounterFS(){
        return new EventLoopCounterFS();
    }

    public static EventLoopCounter fromLoopCounter(){
        return new EventLoopCounter();
    }

    public static WordOccurrence<Response> fromVirtual() {
        return new VirtualCounter();
    }

    public WordOccurrence<Response> fromVirtualThreadFS(){
        return new VirtualCounterFS();
    }

    public static WordOccurrence<Response> fromReactive(){
        return new ReactiveCounter();
    }
}
