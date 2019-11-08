#!/bin/sh 
set -oe pipefail

DIRNAME=`which dirname`
BIN_PATH=$( cd `${DIRNAME} $0`; pwd -P )
APP_HOME=$(${DIRNAME} ${BIN_PATH})
. $BIN_PATH/env.sh docker

echo "Logging system starting now ..." >> ${APP_HOME}/logs/access.log
# Continuously provide logs so that 'docker logs' can produce them
tail -F "${APP_HOME}/logs/access.log" &

echo "java" ${OPTS} "-jar" ${APP_HOME}/boot/${BUILD_JAR}
echo "                     "

# exec java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR}
exec java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR} >/dev/null 2>&1