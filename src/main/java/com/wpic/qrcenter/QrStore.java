package com.wpic.qrcenter;

public interface QrStore {

    Qr load(String url);

    void store(Qr qr);

}
