#!/bin/sh

ctx=$1
ver=$2
fmt=$3

cd target
tar xf $ctx-$ver.$fmt

cd $1

docker build -t bittx/$ctx:v$ver .
cd ..
rm -rf $ctx