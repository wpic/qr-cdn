package com.wpic.qrcenter;

public interface QrStore {

    Qr load(QrRequest request);

    void store(Qr qr);

}
