#!/bin/bash

REMOTE_HOST=sprinklers.local
REMOTE_USER=pi

mvn package

# ssh ${REMOTE_USER}@${REMOTE_HOST} 'rm -rf sprinkler-control ; mkdir sprinkler-control'

rsync target/sprinkler-control*.jar ${REMOTE_USER}@${REMOTE_HOST}:sprinkler-control/sprinkler-control.jar
rsync src/main/config/config-DEV.yml ${REMOTE_USER}@${REMOTE_HOST}:sprinkler-control/config.yml
rsync ~/.m2/repository/com/pi4j/pi4j-native/1.0/pi4j-native-1.0.so  ${REMOTE_USER}@${REMOTE_HOST}:sprinkler-control/libpi4j.so

ssh ${REMOTE_USER}@${REMOTE_HOST} 'cd sprinkler-control ; java -jar sprinkler-control.jar check config.yml'
if [ $? ] ; then
    ssh -t ${REMOTE_USER}@${REMOTE_HOST} 'cd sprinkler-control ; sudo java -cp . -jar sprinkler-control.jar server config.yml'
fi
