# spring-boot-docker
spring boot project package with docker image

Package apps in three different ways:
1. Pack the zip package, the startup does not require spring loader related classes. startup scripts, configuration files, dependent jar packages, log files are stored in directories.
2. Use maven's docker plugin, packaged as docker, the biggest problem with this method is that it is very slow, it is not recommended to use.
3. Use scripts and dockerfile to package as docker, the directory structure is the same as the first one, you can quickly package docker applications.
