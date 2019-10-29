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
ARG JAR_FILE
ADD ${JAR_FILE} /
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]