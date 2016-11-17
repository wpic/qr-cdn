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

package com.wpic.qrcdn.model;

import lombok.*;

import java.awt.Color;

/**
 *
 */
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public final class QrRequest {

    private QrRequest() {
    }

    /**
     * Text which we want to generate QR code from.
     */
    @Getter
    @NonNull
    private String text;

    /**
     * Color of the QR code. Default is Color.BLACK.
     */
    @Getter
    private Color color;

    /**
     * Background color for the QR code.
     */
    @Getter
    private Color bg;

    /**
     * Size of the QR code. Default is 256x256.
     */
    @Getter
    private Size size;

    /**
     * Generate icon from favicon.ico of text, if text is URL and if favicon is available.
     */
    @Getter
    private Boolean icon;

    /**
     * Create new builder.
     * @return return
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class.
     */
    public static final class Builder {

        private String text;

        private Color color;

        private Color bg;

        private Size size = new Size(256, 256);

        private Boolean icon = Boolean.FALSE;

        private Builder() {

        }

        /**
         *
         * @param text text
         * @return
         */
        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        /**
         *
         * @param color color
         * @return return
         */
        public Builder color(final Color color) {
            this.color = color;
            return this;
        }

        /**
         *
         * @param bg bg
         * @return return
         */
        public Builder bg(final Color bg) {
            this.bg = bg;
            return this;
        }

        /**
         *
         * @param size size
         * @return return
         */
        public Builder size(final Size size) {
            this.size = size;
            return this;
        }

        /**
         *
         * @param icon icon
         * @return return
         */
        public Builder icon(final Boolean icon) {
            this.icon = icon;
            return this;
        }

        /**
         *
         * @return return
         */
        public QrRequest build() {
            return new QrRequest(
                    this.text,
                    this.color == null ? Color.BLACK : this.color,
                    this.bg == null ? Color.WHITE : this.bg,
                    this.size,
                    this.icon
            );
        }

        /**
         *
         * @param uri uri
         * @return return
         */
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
                } else if (p.matches("[0-9a-fA-F]{3}") || p.matches("[0-9a-fA-F]{6}")) {
                    if (this.color == null) {
                        this.color = this.hex2Rgb(p);
                    } else if (this.bg == null) {
                        this.bg = this.hex2Rgb(p);
                    } else {
                        break;
                    }
                    start = index + 1;
                } else if (p.equalsIgnoreCase("icon")) {
                    this.icon = Boolean.TRUE;
                    start = index + 1;
                } else {
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
            } else {
                final int i = Integer.parseInt(colorStr, 16);
                return new Color(
                        (i & 0xFF0000) >> 16,
                        (i & 0xFF00) >> 8,
                        i & 0xFF);
            }

        }

    }

}
