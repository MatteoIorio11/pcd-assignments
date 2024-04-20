package pcd.ass02.server.model;

import io.vertx.core.Vertx;
import pcd.ass02.server.model.lib.factory.WordCounterFactory;
import pcd.ass02.server.model.lib.html.Page;
import pcd.ass02.server.model.lib.virtual.html.VirtualCounter;

import java.util.Optional;

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

        //final var c = new VirtualCounter();
        //final Response b = c.getWordOccurrences("./", "java", 2);
        //System.out.println(b.count());
        //System.out.println(b.toJson().encodePrettily());

        // Virtual Thread Test
//        final var vc = WordCounterFactory.fromVirtual();
//        System.out.println(vc
//                .getWordOccurrences("https://en.wikipedia.org/wiki/Main_Page", "the", 2)
//                .count()
//        );

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
