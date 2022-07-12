FROM openjdk:18

COPY target/SpringBootBeginners-0.0.1-SNAPSHOT.jar /home/app/app.jar


ENV JVM_OPTS=""
ENV APP_ARGS=" --server.port=8082 "
ENTRYPOINT java ${JVM_OPTS} -jar /home/app/app.jar ${APP_ARGS}
