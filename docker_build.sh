#!/bin/sh
cd target
tar xf boot-docker-1.0.0.tar.gz

cd boot-docker

docker build -t boot .