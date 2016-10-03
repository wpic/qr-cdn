package com.wpic.qrcenter;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.*;
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
            final String uri = exchange.getRequestURI().substring(1);
            if (uri.length() == 0) {
                final InputStream inputStream = ClassLoader.getSystemResourceAsStream("index.html");
                final byte[] data = new byte[inputStream.available()];
                inputStream.read(data);

                exchange.getRequestHeaders().put(HttpString.tryFromString("Content-Type"), "text/html; charset: UTF-8");
                exchange.getResponseSender().send(ByteBuffer.wrap(data));
            }
            else if ("favicon.ico".equals(uri)) {
                final InputStream inputStream = ClassLoader.getSystemResourceAsStream("favicon.ico");
                final byte[] data = new byte[inputStream.available()];
                inputStream.read(data);

                exchange.getRequestHeaders().put(HttpString.tryFromString("Content-Type"), "image/x-icon");
                exchange.getResponseSender().send(ByteBuffer.wrap(data));
            }
            else {
                final Qr qr = service.get(uri);
                if (qr != null) {
                    exchange.getResponseHeaders().add(HttpString.tryFromString("Content-Type"), qr.getContentType());
                    exchange.getResponseHeaders().add(HttpString.tryFromString("Cache-Control"), "max-stale=31536000");

                    exchange.getResponseSender().send(ByteBuffer.wrap(qr.getData()));
                } else {
                    exchange.setStatusCode(404);
                }
            }
        }

    }

}
