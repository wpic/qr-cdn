package com.wpic.qrcenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;

@ToString
@AllArgsConstructor
public class QrRequest {

    private QrRequest() {

    }

    @Getter
    @NonNull
    private String text;

    @Getter
    private Color color;

    @Getter
    private Color bg;

    @Getter
    private Size size;

    @Getter
    private Boolean icon;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String text;

        private Color color = Color.BLACK;

        private Color bg = Color.WHITE;

        private Size size = new Size(256, 256);

        private Boolean icon = Boolean.FALSE;

        private Builder() {

        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder bg(Color bg) {
            this.bg = bg;
            return this;
        }

        public Builder size(Size size) {
            this.size = size;
            return this;
        }

        public Builder icon(Boolean icon) {
            this.icon = icon;
            return this;
        }

        public QrRequest build() {
            return new QrRequest(
                    this.text,
                    this.color,
                    this.bg,
                    this.size,
                    this.icon
            );
        }

        public Builder parse(final String uri) {
            int index;
            int start = 0;

            while ((index = uri.indexOf('/', start)) > -1) {
                final String p = uri.substring(start, index);

                if (p.matches("[0-9]{1,3}x[0-9]{1,3}")) {
                    this.size = Size.fromString(p);
                    if (!this.size.getWidth().equals(this.size.getHeight())) {
                        throw new IllegalArgumentException(this.size + " has illegal size. It should be rectangular");
                    }
                    if (this.size.getWidth() != 128 && this.size.getWidth() != 256 && this.size.getWidth() != 512) {
                        throw new IllegalArgumentException("Illegal size: " + p);
                    }

                    start = index + 1;
                }
                else if (p.matches("[0-9a-fA-F]{3}") || p.matches("[0-9a-fA-F]{6}")) {
                    this.color = hex2Rgb(p);
                    start = index + 1;
                }
                else if (p.equalsIgnoreCase("icon")) {
                    this.icon = Boolean.TRUE;
                    start = index + 1;
                }
                else {
                    break;
                }
            }

            this.text = uri.substring(start);

            return this;
        }

        private Color hex2Rgb(final String colorStr) {
            if (colorStr.length() == 3) {
                final int i = Integer.parseInt(colorStr, 16);
                return new Color(
                        (i & 0xF00) * (i & 0xF00) >> 8,
                        (i & 0xF0) * (i & 0xF0) >> 4,
                        (i & 0xF) * (i & 0xF)
                );
            }
            else {
                final int i = Integer.parseInt(colorStr, 16);
                return new Color(
                        (i & 0xFF0000) >> 16,
                        (i & 0xFF00) >> 8,
                        i & 0xFF);
            }

        }

    }

}
