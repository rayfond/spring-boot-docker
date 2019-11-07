#!/bin/sh 
set -oe pipefail

DIRNAME=`which dirname`
BIN_PATH=$( cd `${DIRNAME} $0`; pwd -P )
APP_HOME=$(${DIRNAME} ${BIN_PATH})
. $BIN_PATH/env.sh

# if command starts with an option, prepend java
if [ "${1:0:1}" = '-' ]; then
    set -- java "$@"
fi    

# $0 is a script name,
# $1,S2,S3 etc are passed arguments
# so $1 is our command

CMD=$1

case "$CMD" in
    "dev" )
        java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR}
        echo "dev env started."
    ;;


    ## test if the app and end it later
    "test" )
        java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR}
    ;;

    "start" )
    # we can modify files here, using ENV variables passed in
    # "docker create" command. It can't be done during build process.
    # echo "db:$DATABASE_ADDRESS" >> /conf/config.xml
    export BOOT_ENV=prod
    echo "Logging system starting now ..." >> ${APP_HOME}/logs/access.log
    # Continuously provide logs so that 'docker logs' can produce them
    tail -F "${APP_HOME}/logs/access.log" &
    exec java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR} >/dev/null 2>&1
    ;;

    * )
    # Run custom command. Thanks to this line we can still use
    # "docker run our_image /bin/bash" and it will work
    exec $CMD ${@:2}
    ;;
esac