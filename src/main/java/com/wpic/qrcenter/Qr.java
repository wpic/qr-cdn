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

import lombok.*;

import java.awt.Color;
import java.io.Serializable;

/**
 * QR model.
 */
@ToString(exclude = "data")
@AllArgsConstructor
@Builder
public class Qr implements Serializable {

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
     * Background color. Default is Color.WHITE.
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
     * Byte for image data generate by QR code generator.
     */
    @Getter
    private byte[] data;

    /**
     * Image contentType.
     */
    @Getter
    private String contentType;

}
