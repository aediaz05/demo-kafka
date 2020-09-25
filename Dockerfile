FROM openjdk:8-jre-slim

ADD ./target/spring-kafka-0.0.1-SNAPSHOT.jar /usr/local/app/app.jar
RUN echo "java -jar -Dspring.profiles.active=prod /usr/local/app/app.jar" > /usr/local/app/entrypoint.sh

ENTRYPOINT ["/bin/bash", "/usr/local/app/entrypoint.sh"]