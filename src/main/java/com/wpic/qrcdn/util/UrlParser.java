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

package com.wpic.qrcdn.util;

import lombok.Getter;
import lombok.ToString;

/**
 *
 */
@ToString
public final class UrlParser {

    @Getter
    private String color;

    @Getter
    private String size;

    @Getter
    private Boolean icon;

    @Getter
    private String text;

    private UrlParser() {

    }

    /**
     *
     * @param uri uri
     * @return return
     */
    public static UrlParser parse(final String uri) {
        final UrlParser up = new UrlParser();

        int index;
        int start = 0;

        while ((index = uri.indexOf('/', start)) > -1) {
            final String p = uri.substring(start, index);

            if (p.matches("[0-9]{1,3}x[0-9]{1,3}")) {
                up.size = p;
                start = index + 1;
            } else if (p.matches("[0-9a-fA-F]{3,8}")) {
                up.color = p;
                start = index + 1;
            } else if (p.equalsIgnoreCase("icon")) {
                up.icon = Boolean.TRUE;
                start = index + 1;
            } else {
                break;
            }
        }

        up.text = uri.substring(start);

        return up;
    }

}
