package com.wpic.qrcenter;

import lombok.val;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

import static org.testng.Assert.*;


public class ServerTest {

    private Server server;

    @BeforeClass
    public void setUp() throws IOException {
        final QrStore store = new MemoryQrStore();
        final QrGenerator generator = new XingQrGenerator();
        final QrService service = new QrService(store, generator);

        this.server = new Server(service);

        this.server.start();
    }

    @Test
    public void test() throws IOException {
        val client = ClientBuilder.newClient();
        val target = client.target("http://localhost:8080");

        val res1 = target.path("/test").request().get();
        assertEquals(res1.getStatus(), 200);
        assertEquals(res1.getHeaderString("Content-Type"), "image/png");
        assertEquals(
                checksum(this.getClass().getResourceAsStream("/zxing_test.png")),
                checksum(res1.readEntity(InputStream.class)));

        val res2 = target.path("/128x128/test").request().get();
        assertEquals(res2.getStatus(), 200);
        assertEquals(res2.getHeaderString("Content-Type"), "image/png");
        assertEquals(
                checksum(this.getClass().getResourceAsStream("/zxing_128x128_test.png")),
                checksum(res2.readEntity(InputStream.class)));

        val res3 = target.path("/256x256/11AA55/test").request().get();
        assertEquals(res3.getStatus(), 200);
        assertEquals(res3.getHeaderString("Content-Type"), "image/png");
        assertEquals(
                checksum(this.getClass().getResourceAsStream("/zxing_256x256_11AA55_test.png")),
                checksum(res3.readEntity(InputStream.class)));

        val res4 = target.path("/11AA55/256x256/test").request().get();
        assertEquals(res4.getStatus(), 200);
        assertEquals(res4.getHeaderString("Content-Type"), "image/png");
        assertEquals(
                checksum(this.getClass().getResourceAsStream("/zxing_256x256_11AA55_test.png")),
                checksum(res4.readEntity(InputStream.class)));

    }

    private long checksum(final InputStream is) throws IOException {
        val bytes = IOUtils.toByteArray(is);
        val checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        return checksum.getValue();
    }

    @AfterClass
    public void tearDown() {
        this.server.stop();
    }

}
