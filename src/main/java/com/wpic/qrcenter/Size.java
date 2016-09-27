package com.wpic.qrcenter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Size implements Serializable {

    @Getter
    private Integer width;

    @Getter
    private Integer height;

    public static Size fromString(final String size) {
        final String[] parts = size.split("x");
        return new Size(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

}
