/*
 * qr-cdn - 2016
 * http://github.com/wpic/qr-cdn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wpic.qrcdn;

import com.wpic.qrcdn.model.Qr;
import com.wpic.qrcdn.model.QrRequest;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import io.undertow.util.StatusCodes;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 *
 */
public class Server {

    /**
     *
     */
    private final Undertow server;

    /**
     *
     */
    private final QrService service;

    /**
     *
     * @param service service
     */
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

    /**
     *
     */
    public final void start() {
        this.server.start();
    }

    /**
     *
     */
    public final void stop() {
        this.server.stop();
    }

    /**
     *
     */
    private class ImageHandler implements HttpHandler {

        @Override
        public void handleRequest(final HttpServerExchange exchange) throws Exception {
            final String uri = exchange.getRequestURI().substring(1);
            if (uri.length() == 0) {
                final InputStream inputStream = Server.class.getResourceAsStream("/index.html");

                final byte[] buffer = new byte[10240];
                int size;
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while ((size = inputStream.read(buffer)) > 0) {
                    baos.write(buffer, 0, size);
                }

                exchange.getRequestHeaders().put(HttpString.tryFromString("Content-Type"), "text/html; charset: UTF-8");
                exchange.getResponseSender().send(ByteBuffer.wrap(baos.toByteArray()));
            } else if ("favicon.ico".equals(uri)) {
                final InputStream inputStream = Server.class.getResourceAsStream("/favicon.ico");
                final byte[] data = new byte[inputStream.available()];
                inputStream.read(data);

                exchange.getRequestHeaders().put(HttpString.tryFromString("Content-Type"), "image/x-icon");
                exchange.getResponseSender().send(ByteBuffer.wrap(data));
            } else {
                final QrRequest request = QrRequest.builder().parse(exchange.getRequestURI().substring(1)).build();
                final Qr qr = Server.this.service.get(request);
                if (qr != null) {
                    exchange.getResponseHeaders().add(HttpString.tryFromString("Content-Type"), qr.getContentType());
                    exchange.getResponseHeaders().add(HttpString.tryFromString("Cache-Control"), "max-stale=31536000");

                    exchange.getResponseSender().send(ByteBuffer.wrap(qr.getData()));
                } else {
                    exchange.setStatusCode(StatusCodes.NOT_FOUND);
                }
            }
        }

    }

}
