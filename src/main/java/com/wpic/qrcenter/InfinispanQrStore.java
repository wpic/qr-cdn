package com.wpic.qrcenter;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import java.util.concurrent.TimeUnit;

public class InfinispanQrStore implements QrStore {

    private static final Cache<String, byte[]> cache = new DefaultCacheManager().getCache();

    @Override
    public Qr load(final String url) {
        if (cache.containsKey(url)) {
            return new Qr(url, cache.get(url), "image/png");
        }
        return null;
    }

    @Override
    public void store(final Qr qr) {
        cache.put(qr.getUrl(), qr.getData(), 30, TimeUnit.DAYS, 7, TimeUnit.DAYS);
    }

}
