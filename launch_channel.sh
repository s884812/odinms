#!/bin/sh

#!/bin/sh
export CLASSPATH=".:dist/*:lib/*"

if test "$#" -ne 1; then
    echo "./launch_world.sh <worldId>"
else
    java -Xmx600m \
        -Drecvops=recvops.properties \
        -Dsendops=sendops.properties \
        -Dwzpath=wz/ \
        -Dchannel.worldId=$1 \
        -Dchannel.config=channel.properties \
        -Djavax.net.ssl.keyStore=filename.keystore \
        -Djavax.net.ssl.keyStorePassword=passwd \
        -Djavax.net.ssl.trustStore=filename.keystore \
        -Djavax.net.ssl.trustStorePassword=passwd \
        net.channel.ChannelServer \
        -Dcom.sun.management.jmxremote.port=13373 \
        -Dcom.sun.management.jmxremote.password.file=jmxremote.password \
        -Dcom.sun.management.jmxremote.access.file=jmxremote.access
fi
