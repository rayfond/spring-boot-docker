# FROM openjdk:8-jdk-alpine as build
# WORKDIR /workspace/app

# COPY mvnw .
# COPY .mvn .mvn
# COPY pom.xml .
# COPY src src

# RUN --mount=type=cache,target=/home/boot/.m2 ./mvnw package -DskipTests

FROM openjdk:8-jdk-alpine

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


VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-cp","app:app/lib/*","tk.deep.docker.DemoApplication"]