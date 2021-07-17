FROM openjdk:8-jdk-alpine
LABEL maintainer = "Asin Liu  <codezone@163.com>"
# VOLUME /tmp

WORKDIR /ray

COPY lib ./lib
COPY bin ./bin
COPY config ./config
COPY boot ./boot

ENV TZ=Asia/Shanghai
RUN set -eux; \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime; \
    echo $TZ > /etc/timezone
    #&& mkdir logs

ENTRYPOINT ["./bin/entrypoint.sh"]
EXPOSE 8080
CMD ["start"]