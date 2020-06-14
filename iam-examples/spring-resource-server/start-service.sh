#!/bin/sh

echo "Starting spring-resource-server"

until $(curl --silent --output /dev/null -f http://127.0.0.1:8080/services/authentication/iam-admins/iam-admins/.well-known/jwks.json ); do
  echo "Waiting for iam-service to start ..."
  sleep 1
done

java -Xms32m -Xms128M -jar /spring-resource-server-1.0.0-SNAPSHOT.jar