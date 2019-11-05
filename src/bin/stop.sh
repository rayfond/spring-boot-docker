#!/bin/sh -e

APP_NAME=@project.name@
BUILD_JAR="@project.build.finalName@.jar"

PID=$(ps -ef | grep "${BUILD_JAR}" | grep -v 'grep' | awk '{ print $2 }')

if [ -z "${PID}" ]
then
    echo ${APP_NAME} is already stopped.
else
    echo "shutdown application ${APP_NAME} ..."
    kill ${PID} &&  echo ${APP_NAME} stopped successfully.
fi