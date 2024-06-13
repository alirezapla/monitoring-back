FROM liberica-openjdk-alpine:8

EXPOSE 8877
VOLUME /tmp
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8mvn
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xmx2048M","-jar","/app.jar"]
