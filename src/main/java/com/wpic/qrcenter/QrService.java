package com.wpic.qrcenter;

import java.io.IOException;

public class QrService {

    private final QrStore store;

    private final QrGenerator generator;

    public QrService(QrStore store, QrGenerator generator) {
        this.store = store;
        this.generator = generator;
    }

    public Qr get(final String url) throws IOException {
        Qr qr = this.store.load(url);
        if (qr == null) {
            qr = this.generator.generate(url);
            this.store.store(qr);
        }
        return qr;
    }

}
