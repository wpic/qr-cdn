[QR code] is one the best way to share link, text, contact and more with mobile users.
Number of use cases you can use QR code is countless.

**qr-cdn** can covers many of them! 
- Easily embed in your static pages.
- Access fast all around the world (CDN).
- Generate dynamic QR code very fast.

How to use it?
==============
You can simply call the URL, system automatically generates the QR code for you:

    # Simple text
    http://SERVER/sample+text
    
    # From URL
    http://SERVER/http://you/website/page
    
    # Change sizes. If supports 128x128, 256x256 & 512x512
    http://SERVER/128x128/test
    
    # Also color. This one is blue.
    http://SERVER/0011FF/blue+one
    http://SERVER/256x256/1100FF/big+blue+one
    
System & browser cache the code, it's always fast!
    

Build it
========
Simply build it with maven:

    mvn clean install

jar executable file will generate in target folder (qr-cdn-VERSION.jar)

Run it
======
You can use maven:

    mvn compile exec:run
    
Or build the jar file:

    mvn clean install
    
And the run it (Single file, no dependencies is required):

    java -jar target/qr-cdn-VERSION.jar
    
To change the network interface and port you can use system environment variables `HOST` and `PORT`:

    java -DHOST=192.168.0.1 -DPORT=8000 -jar target/qr-cdn-VERSION.jar


Docker
======

You can easily run it with Docker

    mvn clean install
    docker build -t qrcenter .
    docker run -it -p 80:8080 qrcenter

Server is up on port 80.


Change log
==========
* 1.5
  * Change the project name from qr-center to qr-code.
* 1.4
  * Add icon support from favicon.
* 1.2
  * Add background color support.
* 1.1:
  * Add size support.
  * Add color support.
* 1.0: First release.


[QR code]: https://en.wikipedia.org/wiki/QR_code