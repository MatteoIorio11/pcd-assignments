package pcd.ass02.server.model;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import pcd.ass02.server.model.http.HttpServerX;

public class main {

    public static void main(String[] args) {
        Vertx v = Vertx.vertx();
        final AbstractVerticle h = new HttpServerX(15_000);
        v.deployVerticle(h);
    }
}
