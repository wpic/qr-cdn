package com.wpic.qrcenter;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import java.util.concurrent.TimeUnit;

public class InfinispanQrStore implements QrStore {

    private static Cache<String, Qr> cache;

    static {
        try {
            cache = new DefaultCacheManager("infinispan.xml").getCache();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Qr load(final QrRequest request) {
        final String key = request.getText() + "-" + request.getColor().getRGB() + "-" + request.getSize().getWidth();
        return cache.get(key);
    }

    @Override
    public void store(final Qr qr) {
        final String key = qr.getText() + "-" + qr.getColor().getRGB() + "-" + qr.getSize().getWidth();
        cache.put(key, qr);
    }

}
