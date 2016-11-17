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

package com.wpic.qrcdn.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 */
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Size implements Serializable {

    /**
     *
     */
    @Getter
    private Integer width;

    /**
     *
     */
    @Getter
    private Integer height;

    /**
     *
     * @param size size
     * @return return
     */
    public static Size fromString(final String size) {
        final String[] parts = size.split("x");
        return new Size(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

}
