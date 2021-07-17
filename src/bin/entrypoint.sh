#!/bin/sh 
set -oe pipefail

CMD=start

# allow the container to be started with `--user`
if [ "$1" = ${CMD} ]; then

    DIRNAME=$(which dirname)
    BIN_PATH=$(cd $(${DIRNAME} "$0"); pwd -P )
    APP_HOME=$(${DIRNAME} ${BIN_PATH})

    echo "APP_HOME:"$APP_HOME
    echo "BIN_PATH:"$BIN_PATH

    . "$BIN_PATH"/env.sh docker



    param=$(echo "$*" | sed s/${CMD}//g)

    OPTS=${OPTS}${param}

    echo "Param is:[${param}]"
    sh="java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR}"
    echo "$sh"
    echo " "

    echo "Logging system starting now ..." >> ${APP_HOME}/logs/access.log
    echo "============ App started at  $(date) ============" >> ${APP_HOME}/logs/start.log
    # Continuously provide logs so that 'docker logs' can produce them
    tail -F "${APP_HOME}/logs/access.log" &

    ## exec java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR}
    #java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR} >/dev/null 2>&1
    java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR} >> ${APP_HOME}/logs/start.log
else
  exec "$@"
fi