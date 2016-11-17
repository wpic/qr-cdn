package com.wpic.qrcdn;

import com.wpic.qrcdn.util.UrlParser;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UrlParserTest {

    @Test
    public void test() {
        final UrlParser u1 = UrlParser.parse("http://yahoo.com");

        assertNull(u1.getColor());
        assertNull(u1.getIcon());
        assertNull(u1.getSize());
        assertEquals(u1.getText(), "http://yahoo.com");


        final UrlParser u2 = UrlParser.parse("128x128/http://yahoo.com");

        assertNull(u2.getColor());
        assertNull(u2.getIcon());
        assertEquals("128x128", u2.getSize());
        assertEquals(u2.getText(), "http://yahoo.com");


        final UrlParser u3 = UrlParser.parse("96x96/FFEEFF/http://yahoo.com");

        assertEquals(u3.getColor(), "FFEEFF");
        assertNull(u3.getIcon());
        assertEquals(u3.getSize(), "96x96");
        assertEquals(u3.getText(), "http://yahoo.com");


        final UrlParser u4 = UrlParser.parse("96x96/FFEEFF/icon/http://yahoo.com");

        assertEquals(u4.getColor(), "FFEEFF");
        assertEquals(u4.getIcon(), Boolean.TRUE);
        assertEquals(u4.getSize(), "96x96");
        assertEquals(u4.getText(), "http://yahoo.com");


        final UrlParser u5 = UrlParser.parse("FFEEFF/96x96/icon/http://yahoo.com");

        assertEquals(u5.getColor(), "FFEEFF");
        assertEquals(u5.getIcon(), Boolean.TRUE);
        assertEquals(u5.getSize(), "96x96");
        assertEquals(u5.getText(), "http://yahoo.com");
    }

}
