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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 */
public class XingQrGenerator implements QrGenerator {

    /**
     *
     */
    private static final int WIDTH = 250;

    /**
     *
     */
    private static final int HEIGHT = 250;

    @Override
    public final Qr generate(final String url) throws IOException {
        final BitMatrix bm = generateBM(url);
        final BufferedImage bi = toBufferedImage(bm);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", baos);
        final byte[] data = baos.toByteArray();

        return new Qr(url, data, "image/png");
    }

    /**
     *
     * @param path path
     * @return return
     * @throws IOException io
     */
    private BitMatrix generateBM(final String path) throws IOException {
        final Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(
                    path,
                    BarcodeFormat.QR_CODE,
                    WIDTH,
                    HEIGHT,
                    hintMap);
        } catch (WriterException ex) {
            throw new IOException(ex);
        }
        return bitMatrix;
    }

    /**
     *
     * @param matrix matrix
     * @return return
     */
    private BufferedImage toBufferedImage(final BitMatrix matrix) {
        final int width = matrix.getWidth();
        final int height = matrix.getHeight();
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final int[] pixels = new int[width * height];
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[index++] = matrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
            }
        }
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

}
