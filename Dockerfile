FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /home/app
COPY build/libs/stock-screener-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]


