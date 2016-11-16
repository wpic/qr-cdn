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

import net.sf.image4j.codec.ico.ICODecoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 */
public final class IconTools {

    private static final int MAX_ICON_SIZE = 1024 * 1024;

    private IconTools() {

    }

    /**
     * Load favicon from the URL.
     * @param url One URL of the website.
     * @return
     * @throws IOException If icon does not have right format, be very large and etc.
     */
    public static BufferedImage load(final URL url) throws IOException {
        final URL faviconUrl = new URL(url.getProtocol(), url.getHost(), url.getPort(), "/favicon.ico");

        final HttpURLConnection con = (HttpURLConnection) faviconUrl.openConnection();
        con.setReadTimeout(5000);
        con.setConnectTimeout(5000);

        BufferedImage image = null;

        if (con.getResponseCode() == 200) {
            final int length = con.getContentLength();
            if (length < MAX_ICON_SIZE) {
                // We don't read more than 10K it the size is unclear
                final InputStream is = con.getInputStream();

                final ByteArrayOutputStream baos = new ByteArrayOutputStream(length > -1 ? length : MAX_ICON_SIZE);
                final byte[] buffer = new byte[1024];
                int size;

                while ((size = is.read(buffer)) > 0 && baos.size() < MAX_ICON_SIZE) {
                    baos.write(buffer, 0, size);
                }

                if (baos.size() < MAX_ICON_SIZE) {
                    final List<BufferedImage> images = ICODecoder.read(new ByteArrayInputStream(baos.toByteArray()));

                    if (images.size() > 0) {
                        image = images.get(0);
                    }
                }
            }
        }

        con.disconnect();
        return image;
    }

}
