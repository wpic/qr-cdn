package com.wpic.qrcenter;

import org.testng.annotations.Test;

import java.awt.*;

import static org.testng.Assert.*;
import static java.lang.Integer.parseInt;

public class QrRequestTest {

    @Test
    public void test() {
        final QrRequest u1 = QrRequest.builder().parse("http://yahoo.com").build();

        assertEquals(Color.BLACK, u1.getColor());
        assertFalse(u1.getIcon());
        assertEquals(new Size(256, 256), u1.getSize());
        assertEquals(u1.getText(), "http://yahoo.com");


        final QrRequest u2 = QrRequest.builder().parse("128x128/http://yahoo.com").build();

        assertEquals(Color.BLACK, u2.getColor());
        assertFalse(u2.getIcon());
        assertEquals(new Size(128, 128), u2.getSize());
        assertEquals(u2.getText(), "http://yahoo.com");


        final QrRequest u3 = QrRequest.builder().parse("256x256/FFEEFF/http://yahoo.com").build();

        assertEquals(u3.getColor(), new Color(parseInt("FF", 16), parseInt("EE", 16), parseInt("FF", 16)));
        assertFalse(u3.getIcon());
        assertEquals(new Size(256, 256), u3.getSize());
        assertEquals(u3.getText(), "http://yahoo.com");


        final QrRequest u4 = QrRequest.builder().parse("512x512/FFEEFF/icon/http://yahoo.com").build();

        assertEquals(u4.getColor(), new Color(parseInt("FF", 16), parseInt("EE", 16), parseInt("FF", 16)));
        assertEquals(u4.getIcon(), Boolean.TRUE);
        assertEquals(new Size(512, 512), u4.getSize());
        assertEquals(u4.getText(), "http://yahoo.com");


        final QrRequest u5 = QrRequest.builder().parse("FFEEFF/512x512/icon/http://yahoo.com").build();

        assertEquals(u5.getColor(), new Color(parseInt("FF", 16), parseInt("EE", 16), parseInt("FF", 16)));
        assertEquals(u5.getIcon(), Boolean.TRUE);
        assertEquals(new Size(512, 512), u5.getSize());
        assertEquals(u5.getText(), "http://yahoo.com");
    }

}
