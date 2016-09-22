FROM java:8
COPY target/qr-center-1.0-SNAPSHOT.jar /usr/bin
WORKDIR /usr/bin
EXPOSE 8080
CMD ["java", "-jar", "qr-center-1.0-SNAPSHOT.jar"]