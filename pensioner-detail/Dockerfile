FROM openjdk:11
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
ARG CSV_FILE=./target/classes/pensioners.csv
COPY ${JAR_FILE} pensioners.csv
ENTRYPOINT ["java","-jar","app.jar"]