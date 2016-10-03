/*
 * qr-cdn - 2016
 * http://github.com/abdollahpour/qr-cdn
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

package com.wpic.qrcenter;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

/**
 *
 */
public class InfinispanQrStore implements QrStore {

    /**
     *
     */
    private static Cache<String, byte[]> cache;

    static {
        try {
            cache = new DefaultCacheManager("infinispan.xml").getCache();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public final Qr load(final String url) {
        if (cache.containsKey(url)) {
            return new Qr(url, cache.get(url), "image/png");
        }
        return null;
    }

    @Override
    public final void store(final Qr qr) {
        // Stores it fast and don't care about the errors, any error happens (like lake of memory) system still works.
        cache.putForExternalRead(qr.getUrl(), qr.getData());
    }

}
