#!/bin/sh
export CLASSPATH=".:dist/*:lib/*"

java \
	-Xmx600m \
	-Drecvops=recvops.properties \
	-Dsendops=sendops.properties \
	-Dwzpath=wz/ \
	-Dlogin.config=login.properties \
	-Djavax.net.ssl.keyStore=filename.keystore \
	-Djavax.net.ssl.keyStorePassword=passwd \
	-Djavax.net.ssl.trustStore=filename.keystore \
	-Djavax.net.ssl.trustStorePassword=passwd \
	net.login.LoginServer
