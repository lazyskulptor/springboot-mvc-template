#!/bin/bash

echo "The application will start in ${WAITING_TIME_DEPENDENT_SERVICES}s..." && sleep ${WAITING_TIME_DEPENDENT_SERVICES}

exec java ${JAVA_OPTS} -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "user.lazyskulptor.ecommerce.EcommerceApplication"  "$@"
