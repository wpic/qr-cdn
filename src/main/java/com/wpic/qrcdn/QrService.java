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

package com.wpic.qrcdn;

import com.wpic.qrcdn.generator.QrGenerator;
import com.wpic.qrcdn.model.Qr;
import com.wpic.qrcdn.model.QrRequest;
import com.wpic.qrcdn.store.QrStore;

import java.io.IOException;

/**
 *
 */
public class QrService {

    /**
     *
     */
    private final QrStore store;

    /**
     *
     */
    private final QrGenerator generator;

    /**
     *
     * @param store store
     * @param generator generator
     */
    public QrService(final QrStore store, final QrGenerator generator) {
        this.store = store;
        this.generator = generator;
    }

    /**
     *
     * @param request request
     * @return return
     * @throws IOException io
     */
    public final Qr get(final QrRequest request) throws IOException {
        Qr qr = this.store.load(request);
        if (qr == null) {
            qr = this.generator.generate(request);

            this.store.store(request, qr);
        }
        return qr;
    }

}
