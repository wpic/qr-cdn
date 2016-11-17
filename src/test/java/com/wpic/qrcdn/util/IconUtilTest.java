package com.wpic.qrcdn.util;

import com.wpic.qrcdn.util.IconUtil;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;

import static org.testng.Assert.*;


/**
 *
 */
public class IconUtilTest {

    @Test
    public void test() throws IOException {
        final URL url = new URL("http://www.nurkiewicz.com");
        assertNotNull(IconUtil.load(url));
    }

}
