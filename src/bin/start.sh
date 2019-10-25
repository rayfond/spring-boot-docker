#!/usr/bin/env bash
# 注意项目中所有的脚本都使用sh 标准，可以在linux, unix系统上直接运行，当使用bash shell语法检查工具检查时，
# 可能会造成脚本只能在linux上运行的风险！

DIRNAME=`which dirname`
BIN_PATH=$( cd `${DIRNAME} $0`; pwd -P )
APP_HOME=$(${DIRNAME} ${BIN_PATH})
. $BIN_PATH/env.sh
#echo "nohup java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR} > nohubp.out 2>&1 & "
nohup java ${OPTS} -jar ${APP_HOME}/boot/${BUILD_JAR} > nohup.out 2>&1 &

echo "Starting application now ..."
PID=$(ps -ef | grep "${BUILD_JAR}" | grep -v 'grep' | awk '{ print $2 }')
echo  ${PID} > ${APP_HOME}"/pid"
echo "Application is started!"