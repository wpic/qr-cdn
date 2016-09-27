package com.wpic.qrcenter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;
import java.io.Serializable;

@ToString(exclude = "data")
@AllArgsConstructor
@Builder
public class Qr implements Serializable {

    @Getter
    private String text;

    @Getter
    private Color color;

    @Getter
    private Size size;

    @Getter
    private Boolean icon;

    @Getter
    private byte[] data;

    @Getter
    private String contentType;

}
