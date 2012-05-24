#!/bin/sh

echo "************ UNDEPLOYING *******************"
asadmin undeploy baza_jsf
echo "************ BUILDING **********************"
mvn package
echo "************ DEPLOYING *********************"
asadmin deploy target/baza_jsf.war
