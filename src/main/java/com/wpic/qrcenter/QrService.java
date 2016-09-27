package com.wpic.qrcenter;

import java.io.IOException;

public class QrService {

    private final QrStore store;

    private final QrGenerator generator;

    public QrService(QrStore store, QrGenerator generator) {
        this.store = store;
        this.generator = generator;
    }

    public Qr get(final QrRequest request) throws IOException {
        Qr qr = this.store.load(request);
        if (qr == null) {
            qr = this.generator.generate(request);
            this.store.store(qr);
        }
        return qr;
    }

}
