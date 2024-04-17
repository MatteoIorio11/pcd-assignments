package pcd.ass02.server.model;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Objects;

public class HttpServerX extends AbstractVerticle {
    private final int port;
    public HttpServerX(final int port){
        this.port = port;
    }

    public void start(){
        final HttpServer httpServer = vertx.createHttpServer();
        httpServer
                .requestHandler(this.defineRoutes())
                .listen(this.port);

    }

    private Router defineRoutes(){
        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        return router;
    }

}
