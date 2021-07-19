FROM openjdk:8-jdk-alpine
LABEL maintainer = "Asin Liu  <codezone@163.com>"

WORKDIR /ray



#COPY lib ./lib
#COPY bin ./bin
#COPY config ./config
#COPY boot ./boot

# 对于多个目录我们可以合并成为一层
# 此种方式将失去对lib bin config 三层的cache
# 最适合的方式是单独将boot的copy写入一层
COPY . /ray





#ENV TZ=Asia/Shanghai
#RUN set -eux; \
#    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime; \
#    echo $TZ > /etc/timezone
    #&& mkdir logs

ENTRYPOINT ["./bin/entrypoint.sh"]
EXPOSE 8080
CMD ["start"]