#FROM openjdk:8
#EXPOSE 8080
#ADD target/springboot-k8s-demo.jar springboot-k8s-demo.jar
#ENTRYPOINT ["java","-jar","/springboot-k8s-demo.jar"]

FROM amazoncorretto:11

# Copy the pre-built jar file
#COPY prototype-0.0.1-SNAPSHOT.jar .
ADD target/prototype-0.0.1-SNAPSHOT.jar prototype-0.0.1-SNAPSHOT.jar

EXPOSE 8081

# Run the application
CMD ["java", "-jar", "prototype-0.0.1-SNAPSHOT.jar"]

