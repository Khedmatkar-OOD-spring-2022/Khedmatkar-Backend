FROM gradle:7.5.1-jdk18 as builder
WORKDIR /khedmatkar
COPY . .
RUN gradle bootJar

FROM openjdk:18
WORKDIR /khedmatkar
COPY --from=builder /khedmatkar/build/libs/demo-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "/khedmatkar/demo-0.0.1-SNAPSHOT.jar"]
