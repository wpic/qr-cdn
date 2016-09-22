package com.wpic.qrcenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString(exclude = "data")
@AllArgsConstructor
public class Qr {

    @Getter
    private String url;

    @Getter
    private byte[] data;

    @Getter
    private String contentType;

}
