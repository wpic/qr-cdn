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

package com.wpic.qrcdn.store;

import com.wpic.qrcdn.model.Qr;
import com.wpic.qrcdn.model.QrRequest;
import lombok.NonNull;

/**
 * Store and retrieve QR code.
 */
public interface QrStore {

    /**
     * Load QR.
     * @param request request
     * @return return QR code or null if it does not find.
     */
    Qr load(@NonNull QrRequest request);

    /**
     * Store QR code, for given request.
     * @param request request
     * @param qr qr
     */
    void store(@NonNull QrRequest request, @NonNull Qr qr);

}
