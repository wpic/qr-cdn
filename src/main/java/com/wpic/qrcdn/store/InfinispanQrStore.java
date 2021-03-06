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

package com.wpic.qrcdn.store;

import com.wpic.qrcdn.model.Qr;
import com.wpic.qrcdn.model.QrRequest;
import lombok.NonNull;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

/**
 * Store and retrieve QR code in infinispan.
 */
public class InfinispanQrStore implements QrStore {

    /**
     * Infinispan cache.
     */
    private static Cache<Integer, Qr> cache;

    static {
        // Load if from configuration file.
        try {
            cache = new DefaultCacheManager("infinispan.xml").getCache();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public final Qr load(@NonNull final QrRequest request) {
        return cache.get(request.hashCode());
    }

    @Override
    public final void store(@NonNull final QrRequest qrRequest, @NonNull final Qr qr) {
        // Store it in catch, but if it fails, just ignore.
        cache.putForExternalRead(qrRequest.hashCode(), qr);
    }

}
