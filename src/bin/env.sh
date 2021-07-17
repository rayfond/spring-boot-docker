#!/bin/sh

#APP_NAME=boot-docker
#BUILD_JAR=boot-docker-1.0.0.jar
APP_NAME=@project.name@
BUILD_JAR=@project.build.finalName@.jar

JAVA=`which java`
UNAME=`which uname`
GREP=`which egrep`
CUT=`which cut`
READLINK=`which readlink`
XARGS=`which xargs`
DIRNAME=`which dirname`
MKTEMP=`which mktemp`
RM=`which rm`
CAT=`which cat`
SED=`which sed`

if [ -z ${JAVA} ]; then
    echo "java is missing - check beginning of \"$0\" file for details."
    exit 1
fi

if [ -z "$UNAME" -o -z "$GREP" -o -z "$CUT" -o -z "$MKTEMP" -o -z "$RM" -o -z "$CAT" -o -z "$SED" ]; then
    # xmessage "Required tools are missing - check beginning of \"$0\" file for details."
    echo "Required tools are missing - check beginning of \"$0\" file for details."
    exit 1
fi

# OS_TYPE=`"$UNAME" -s`

JAVA_HOME=$(${JAVA} -XshowSettings:properties -version 2>&1 | sed '/^[[:space:]]*java\.home/!d;s/^[[:space:]]*java\.home[[:space:]]*=[[:space:]]*//')
CLZ_VERSION=$(${JAVA} -XshowSettings:properties -version 2>&1 | sed '/java.class.version/!d' | awk '{print $3}')
GE_JDK8=52.0

if [ ${CLZ_VERSION} \< ${GE_JDK8} ]; then
    echo "App need JDK8 or later version. please run java -version to check the JDK version."
    exit 1
fi

                                                                                 
DV=1.8.0_131
TV=10.0.0
CONTAINER=docker
RUNC=""

JV=$(${JAVA} -XshowSettings:properties -version 2>&1 | sed '/java.version =/!d' | awk '{print $3}')

for i in "$@"
do
    [ "$i" = "$CONTAINER" ] && { RUNC="T"; break; }
done

# Check if the java version is little than 1.8.0_131
[ "$RUNC" = "T" ] && { echo "Container env detected..."; } && [ "$JV" \< "$DV" ] && { echo "java version in container must be greater than ${DV}" ; exit 1; }


# Set the JVM param for java 8 (version 1.8.0_131+)  or java 9

[ "$JV" \> "$DV" ] && [ "$JV" \< "$TV" ] && OPTS=" -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 "

mkd(){
    if [ ! -d $1 ]; then
        mkdir -p $1 
    fi    
}

getOptions(){
    OPTIONS_FILE=${APP_HOME}"/bin/"$1
    [ -r ${OPTIONS_FILE} ] && { OPTION=" "`${CAT} ${OPTIONS_FILE} | ${GREP} -v "^#.*" | ${GREP} -v "^$" `; } || echo "Waring: Can't find option file : ${OPTIONS_FILE}."
}

## Unpack spring boot package BOOT-INF example:
## OPTS=" -Dloader.path=${APP_HOME}/lib,${APP_HOME}/config "

# Not a container env.
if [ -z "$RUNC" ]; then
    echo "Host env detected... "
    OPTION_FILES='vm.option app.option'
    for f in $OPTION_FILES ; do
        getOptions $f
        OPTS=${OPTS}${OPTION}
    done
fi


# OPTS="-XshowSettings:vm ${OPTS}"
OPTS=" -XshowSettings:vm"${OPTS}

# GC log
GC_LOG_DIR=`pwd`"/logs/"
APP_LOG_DIR=${APP_HOME}"/logs/"

mkd $GC_LOG_DIR
mkd $APP_LOG_DIR

echo "Environment is ready, starting application now..."
echo "Java options:"

for i in $OPTS
do 
    echo "    "$i
done 