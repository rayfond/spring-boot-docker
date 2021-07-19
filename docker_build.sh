#!/bin/sh
# Three params

# ${project.artifactId}
ctx=${1:-"boot-docker"}

#${project.version}
ver=${2:-"1.0.0"}

# ${pkg.format}
fmt=${3:-"tar.gz"}

cd target

tar xf "$ctx-$ver.$fmt"

# mv "$ctx"/boot "$ctx"/.boot

docker build -t "bittx/$ctx:v$ver" $ctx

rm -rf $ctx