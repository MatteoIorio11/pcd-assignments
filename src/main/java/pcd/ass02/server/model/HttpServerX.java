package pcd.ass02.server.model;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpServerX extends AbstractVerticle {
    private final int port;
    private final static String PATH_NAME_TO_FIND = "name";
    private final static String PATH_MODE = "mode";
    private final static String PATH_DEPTH = "deep";
    private final static String PATH_EXPLORE = "explore";
    private final static String PATH_DIVIDER = "/";
    private final static String PATH_PARAMETER = "/:";
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
        router
                .get(HttpServerX.PATH_DIVIDER + HttpServerX.PATH_EXPLORE +
                        HttpServerX.PATH_PARAMETER + HttpServerX.PATH_NAME_TO_FIND +
                        HttpServerX.PATH_PARAMETER + HttpServerX.PATH_MODE +
                        HttpServerX.PATH_PARAMETER + HttpServerX.PATH_DEPTH)
                .respond(context -> {
                    final String name = context.pathParam(HttpServerX.PATH_NAME_TO_FIND);
                    final String modality = context.pathParam(HttpServerX.PATH_MODE);
                    final String depth = context.pathParam(HttpServerX.PATH_DEPTH);
                    this.log("Request received, name : " + name + " with the following modality: " + modality + " with the following depth: " + depth);
                    
                    return context
                            .response()
                            .putHeader("Content-Type", "application/json")
                            .end(name);
                });
        return router;
    }

    private void log(final String message){
        System.out.println("[HttpServerX] " + message);
    }
}
