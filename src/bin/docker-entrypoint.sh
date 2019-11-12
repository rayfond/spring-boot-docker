#!/bin/sh 
set -oe pipefail

CMD=start

# allow the container to be started with `--user`
if [ "$1" = ${CMD} ]; then

    DIRNAME=`which dirname`
    BIN_PATH=$( cd `${DIRNAME} $0`; pwd -P )
    APP_HOME=$(${DIRNAME} ${BIN_PATH})
    . "$BIN_PATH"/env.sh docker

    echo "Logging system starting now ..." >> ${APP_HOME}/logs/access.log
    # Continuously provide logs so that 'docker logs' can produce them
    tail -F "${APP_HOME}/logs/access.log" &

    param=`echo $* | sed s/${CMD}//g`

    OPTS=${OPTS}${param}

    echo "Param is:[${param}]"
    echo "java" ${OPTS} "-jar" ${APP_HOME}/boot/${BUILD_JAR}
    echo "                     "
    ## exec java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR}
    exec java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR} >/dev/null 2>&1
fi

exec "$@"