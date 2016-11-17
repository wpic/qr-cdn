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

package com.wpic.qrcdn.generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wpic.qrcdn.util.IconUtil;
import com.wpic.qrcdn.model.Qr;
import com.wpic.qrcdn.model.QrRequest;
import com.wpic.qrcdn.model.Size;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

/**
 *
 */
public class XingQrGenerator implements QrGenerator {

    @Override
    public final Qr generate(final QrRequest request) throws IOException {
        BufferedImage icon = null;
        if (Boolean.TRUE.equals(request.getIcon())) {
            try {
                final URL url = new URL(request.getText());
                icon = IconUtil.load(url);
            } catch (Exception e) {
                e.printStackTrace();
                // ignore, it's not URL or icon has problem
            }
        }

        final BitMatrix bm = this.generateBM(request.getText(), request.getSize(), icon != null);
        final BufferedImage bi = this.toBufferedImage(bm, request.getColor(), request.getBg());

        if (icon != null) {
            this.addIcon(bi, icon);
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", baos);
        final byte[] data = baos.toByteArray();

        return Qr.builder()
                .text(request.getText())
                .size(request.getSize())
                .color(request.getColor())
                .icon(request.getIcon())
                .data(data)
                .contentType("image/png")
                .build();
    }

    private void addIcon(final BufferedImage bi, final BufferedImage icon) {
        final int w = bi.getWidth() / 5;
        final int h = bi.getHeight() / 5;

        final Graphics g = bi.getGraphics();
        g.drawImage(icon, w * 2, h * 2, w, h, null);
    }

    /**
     *
     * @param path path
     * @param size size
     * @return return
     * @throws IOException io
     */
    private BitMatrix generateBM(final String path, final Size size, final boolean high) throws IOException {
        final Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, high ? ErrorCorrectionLevel.H : ErrorCorrectionLevel.L);
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(
                    path,
                    BarcodeFormat.QR_CODE,
                    size.getWidth(),
                    size.getHeight(),
                    hintMap);
        } catch (WriterException ex) {
            throw new IOException(ex);
        }
        return bitMatrix;
    }

    /**
     *
     * @param matrix matrix
     * @param color color
     * @param bg bg
     * @return return
     */
    private BufferedImage toBufferedImage(final BitMatrix matrix, final Color color, final Color bg) {
        final int width = matrix.getWidth();
        final int height = matrix.getHeight();
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final int[] pixels = new int[width * height];
        int index = 0;

        final int pixelColor = color.getRGB();
        final int bgColor = bg.getRGB();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[index++] = matrix.get(x, y) ? pixelColor : bgColor;
            }
        }
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

}
