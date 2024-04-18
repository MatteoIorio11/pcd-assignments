package pcd.ass02.server.model;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import pcd.ass02.server.model.http.HttpServerX;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.event.EventLoopCounter;
import pcd.ass02.server.model.lib.response.Response;

public class main {

    public static void main(String[] args) {
        //Vertx v = Vertx.vertx();
        //final AbstractVerticle h = new HttpServerX(15_000);
        //v.deployVerticle(h);

        //var r = new Response();
        //r.addFile("ciao", 0);
        //r.addFile("zoo", 1);
        //r.addFile("oo", 2);
        //System.out.println(r.toJson());
        Vertx vertx = Vertx.vertx();
        final var l = new EventLoopCounter();
        vertx.deployVerticle(l);
        final Future<Response> z = l.getWordOccurrences("./", "java", 10);
        z.onSuccess(a ->
                System.out.println(a.toJson().encodePrettily()));
        vertx.close();
    }
}
