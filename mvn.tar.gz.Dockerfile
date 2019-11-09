#WORKDIR /usr/share/elasticsearch

# RUN set -ex \
# 	&& printf "The complete list is %s\n" "$?" \
#     && for path in \
# 		./data \
# 		./logs \
# 		./config \
# 		./config/scripts \
# 	; do \
# 		mkdir -p "$path"; \
# 		#chown -R boot:docker "$path"; \
# 	done \
#     && printf "The complete list is %s\n" "$?"

# COPY config ./config

# New layer will add:  COPY ADD RUN
FROM openjdk:8-jdk-alpine
LABEL maintainer = "Asin Liu  <codezone@163.com>"
VOLUME /tmp
ARG APP_HOME
ARG JAR_FILE
ENV TZ=Asia/Shanghai
RUN set -eux; \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime; \
    echo $TZ > /etc/timezone
    
ADD ${JAR_FILE} /
WORKDIR /${APP_HOME}

CMD ["./bin/docker-entrypoint.sh"]




#ARG UID=1000
#ARG GID=1000
## User name
#ARG UN=ray
## Group name
#ARG GN=ray
#ARG HOME=/home/${UN}







# RUN set -eux; \
#     addgroup --gid ${GID} ${GN}; \
#     adduser -u ${UID} ${UN} -DSG ${GN} -h ${HOME} -s /bin/sh; \
#     chown -R ray:ray ${HOME}
    
# ADD ${JAR_FILE} /home/${UN}/

# RUN set -eux; \
#     chown -R ray:ray ${HOME}
# USER ray
# WORKDIR /home/ray/${APP_HOME}

### entry-point.sh ###
# ENTRYPOINT ["./bin/docker-entrypoint.sh"]
#CMD ["./bin/docker-entrypoint.sh"]
# ENTRYPOINT ["./bin/app2.sh"]
# CMD ["start"]
################
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
