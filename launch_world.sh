#!/bin/sh
export CLASSPATH=".:dist/*:lib/*"

if test "$#" -ne 1; then
    echo "./launch_world.sh <worldId>"
else
    java -Xmx600m \
        -Drecvops=recvops.properties \
        -Dsendops=sendops.properties \
        -Dwzpath=wz/ \
        -Dworld.worldId=$1 \
        -Djavax.net.ssl.keyStore=filename.keystore \
        -Djavax.net.ssl.keyStorePassword=passwd \
        -Djavax.net.ssl.trustStore=filename.keystore \
        -Djavax.net.ssl.trustStorePassword=passwd \
        -server net.world.WorldServer
fi
