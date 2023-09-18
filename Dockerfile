FROM eclipse-temurin:11
EXPOSE 8080
ADD target/restaurant-service.jar restaurant-service.jar
ENTRYPOINT ["java", "-jar", "restaurant-service.jar"]