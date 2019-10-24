#!/usr/bin/env bash
DIRNAME=`which dirname`

BIN_PATH=$( cd `${DIRNAME} $0`; pwd -P )
APP_HOME=$(${DIRNAME} ${BIN_PATH})
echo $APP_HOME

. $BIN_PATH/env.sh

java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR}

echo "                       "
echo "Application is started!"
