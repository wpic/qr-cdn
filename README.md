Build it
========
Simply build it with maven:

    mvn clean install

jar executable file will generate in target folder (qr-center-VERSION.jar)

Run it
======
You can use maven:

    mvn compile exec:run
    
Or build the jar file:

    mvn clean install
    
And the run it (Single file, no dependencies is required):

    java -jar target/qr-center-VERSION.jar
    
To change the network interface and port you can use system environment variables `HOST` and `PORT`:

    java -DHOST=192.168.0.1 -DPORT=8000 -jar target/qr-center-VERSION.jar


Docker
======

You can easily run it with Docker

    mvn clean install
    docker build -t qrcenter .
    docker run -it -p 80:8080 qrcenter

Server is up on port 80.