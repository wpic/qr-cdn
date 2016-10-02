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

/**
 *
 */
public final class App {

    /**
     *
     */
    private App() {

    }

    /**
     *
     * @param args args
     * @throws Exception ex
     */
    public static void main(final String[] args) throws Exception {
        final QrStore store = new InfinispanQrStore();
        final QrGenerator generator = new XingQrGenerator();
        final QrService service = new QrService(store, generator);

        final Server server = new Server(service);
        server.start();
    }

}
