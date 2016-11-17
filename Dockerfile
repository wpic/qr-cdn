FROM java:8
COPY qr-cdn.jar /usr/bin
WORKDIR /usr/bin
EXPOSE 8080
CMD ["java", "-jar", "qr-cdn.jar"]