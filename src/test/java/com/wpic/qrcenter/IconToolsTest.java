package com.wpic.qrcenter;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;

import static org.testng.Assert.*;


/**
 *
 */
public class IconToolsTest {

    @Test
    public void test() throws IOException {
        final URL url = new URL("http://www.nurkiewicz.com");
        assertNotNull(IconTools.load(url));
    }

}
