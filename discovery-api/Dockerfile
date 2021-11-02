FROM openjdk:11 AS builder

WORKDIR /service-discovery

COPY . .

RUN ./mvnw package

FROM openjdk:11 AS prod

WORKDIR /service-discovery

COPY --from=builder /service-discovery/target/discovery-api-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "discovery-api-0.0.1-SNAPSHOT.jar"]
