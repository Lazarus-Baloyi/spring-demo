FROM openjdk:11-jre-slim
VOLUME /tmp
COPY spring-demo-port/target/*.war spring-demo-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/spring-demo-0.0.1-SNAPSHOT.war"]