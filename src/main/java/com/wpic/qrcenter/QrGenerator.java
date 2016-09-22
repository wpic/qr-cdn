package com.wpic.qrcenter;

import java.io.IOException;

public interface QrGenerator {

    Qr generate(String url) throws IOException;

}
