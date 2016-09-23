package com.wpic.qrcenter;

import lombok.Getter;
import lombok.ToString;

@ToString
public class UrlParser {

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

    public static UrlParser parse(final String uri) {
        final UrlParser up = new UrlParser();

        int index;
        int start = 0;

        while ((index = uri.indexOf('/', start)) > -1) {
            final String p = uri.substring(start, index);

            if (p.matches("[0-9]{1,3}x[0-9]{1,3}")) {
                up.size = p;
                start = index + 1;
            }
            else if (p.matches("#[0-9a-fA-F]{3,8}")) {
                up.color = p.substring(1);
                start = index + 1;
            }
            else if (p.equalsIgnoreCase("icon")) {
                up.icon = Boolean.TRUE;
                start = index + 1;
            }
            else {
                break;
            }
        }

        up.text = uri.substring(start);

        return up;
    }

}
