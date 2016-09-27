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

    @Override
    public Qr generate(final QrRequest request) throws IOException {
        final BitMatrix bm = generateBM(request.getText(), request.getSize());
        final BufferedImage bi = toBufferedImage(bm, request.getColor(), request.getBg());

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

    private BitMatrix generateBM(final String path, final Size size) throws IOException {
        final Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
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

    private BufferedImage toBufferedImage(final BitMatrix matrix, final Color color, final Color bg) {
        final int width = matrix.getWidth();
        final int height = matrix.getHeight();
        final BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
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
