FROM openjdk:17-jdk

LABEL authors="malak"

COPY target/social-media-app.jar .

EXPOSE 8083

ENTRYPOINT ["java", "-jar" , "social-media-app.jar"]
