package pcd.ass02.server.model;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpServerX extends AbstractVerticle {
    private final int port;
    private final static String PATH_DIRECTORY = "directory";
    private final static String PATH_MODE = "mode";
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
                .get("/explore/:" + HttpServerX.PATH_DIRECTORY + "/:" + HttpServerX.PATH_MODE)
                .respond(context -> {
                    final String directoryName = context.pathParam(HttpServerX.PATH_DIRECTORY);
                    final String modality = context.pathParam(HttpServerX.PATH_MODE);
                    this.log("Request received, directory : " + directoryName + " with the following modality: " + modality);
                    return context
                            .response()
                            .putHeader("Content-Type", "application/json")
                            .end(directoryName);
                });
        return router;
    }

    private void log(final String message){
        System.out.println("[HttpServerX] " + message);
    }
}
