package com.wpic.qrcenter;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.nio.ByteBuffer;

public class Server {

    private final Undertow server;

    private final QrService service;

    public Server(final QrService service) {
        this.service = service;

        this.server = Undertow.builder()
                .addHttpListener(
                        Integer.parseInt(System.getProperty("PORT", "8080")),
                        System.getProperty("HOST", "0.0.0.0")
                )
                .setHandler(new ImageHandler())
                .build();
    }


    public void start() {
        this.server.start();
    }

    public void stop() {
        this.server.stop();
    }

    private class ImageHandler implements HttpHandler {

        @Override
        public void handleRequest(final HttpServerExchange exchange) throws Exception {
            final Qr qr = service.get(exchange.getRequestURI().substring(1));
            if (qr != null) {
                exchange.getResponseHeaders().add(HttpString.tryFromString("Content-Type"), qr.getContentType());
                exchange.getResponseHeaders().add(HttpString.tryFromString("Cache-Control"), "max-stale=31536000");

                exchange.getResponseSender().send(ByteBuffer.wrap(qr.getData()));
            }
            else {
                exchange.setStatusCode(404);
            }
        }

    }

}
