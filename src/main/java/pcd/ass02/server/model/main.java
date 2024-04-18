package pcd.ass02.server.model;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import pcd.ass02.server.model.http.HttpServerX;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.event.EventLoopCounter;
import pcd.ass02.server.model.lib.response.Response;
import pcd.ass02.server.model.lib.virtual.VirtualCounter;

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

        final var c = new VirtualCounter();
        final Response b = c.getWordOccurrences("./", "java", 10);
        System.out.println(b.toJson().encodePrettily());
    }
}
