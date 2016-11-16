FROM java:8
COPY qr-center.jar /usr/bin
WORKDIR /usr/bin
EXPOSE 8080
CMD ["java", "-jar", "qr-center.jar"]