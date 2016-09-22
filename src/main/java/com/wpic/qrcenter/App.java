package com.wpic.qrcenter;

public class App {

    public static void main(final String[] args) throws Exception {
        final QrStore store = new InfinispanQrStore();
        final QrGenerator generator = new XingQrGenerator();
        final QrService service = new QrService(store, generator);

        final Server server = new Server(service);
        server.start();
    }

}
