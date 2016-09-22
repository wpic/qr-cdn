package com.wpic.qrcenter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class XingQrGenerator implements QrGenerator {

    private static final int WIDTH = 250;

    private static final int HEIGHT = 250;

    @Override
    public Qr generate(final String url) throws IOException {
        final BitMatrix bm = generateBM(url);
        final BufferedImage bi = toBufferedImage(bm);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "PNG", baos);
        final byte[] data = baos.toByteArray();

        return new Qr(url, data, "image/png");
    }

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

    private BufferedImage toBufferedImage(final BitMatrix matrix) {
        final int width = matrix.getWidth();
        final int height = matrix.getHeight();
        final BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
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
