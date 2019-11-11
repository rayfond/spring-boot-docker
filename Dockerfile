FROM openjdk:8-jdk-alpine
LABEL maintainer = "Asin Liu  <codezone@163.com>"
VOLUME /tmp

WORKDIR /ray

COPY lib ./lib
COPY bin ./bin
COPY config ./config
COPY boot ./boot

CMD ["./bin/docker-entrypoint.sh"]