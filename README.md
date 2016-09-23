Docker
======

You can easily run it with Docker

    mvn clean install
    docker build -t qrcenter .
    docker run -it -p 80:8080 qrcenter

Server is up on port 80.