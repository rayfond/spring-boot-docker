#!/bin/sh
# Three params :
# ${project.artifactId}
ctx=$1

#${project.version}
ver=$2

# ${pkg.format}
fmt=$3

jarFile=$1.$2.$3
home=/app

mkdir tmp
mv target/$jarFile ./tmp

echo $jarFile
tree .
echo $home
#docker build --build-arg APP_HOME="$home" --build-arg JAR_FILE="$jarFile"  . -t bittx/"$ctx:v$ver"
docker build -f ./Dockerfile \
--build-arg APP_HOME="${home}"  \
--build-arg JAR_FILE="${jarFile}" \
-t bittx/$ctx:v$ver ./tmp

rm -rf tmp
