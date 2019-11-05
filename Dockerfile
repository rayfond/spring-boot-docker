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
ADD ${JAR_FILE} /
WORKDIR /${APP_HOME}
# CMD ["/bin/sh","./bin/test.sh"]
# ENTRYPOINT ["/bin/sh","./bin/start.sh"]

### bittx.sh ###
ENTRYPOINT ["/bin/sh","./bin/app.sh"]
CMD ["start"]
################


#ENTRYPOINT ["./bin/start.sh"]
#CMD ["bin/sh","-c","bin/start.sh"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
