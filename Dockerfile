FROM java:8
COPY qr-center-1.1.jar /usr/bin
WORKDIR /usr/bin
EXPOSE 8080
CMD ["java", "-jar", "qr-center-1.1.jar"]