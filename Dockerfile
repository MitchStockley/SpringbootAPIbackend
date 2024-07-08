# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-17

WORKDIR /RegisterApp
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
