package com.wpic.qrcenter;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import java.util.concurrent.TimeUnit;

public class InfinispanQrStore implements QrStore {

    private static Cache<String, byte[]> cache;

    static {
        try {
            cache = new DefaultCacheManager("infinispan.xml").getCache();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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
