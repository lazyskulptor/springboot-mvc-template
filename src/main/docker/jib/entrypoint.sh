#!/bin/bash

exec java ${JAVA_OPTS} -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "user.lazyskulptor.ecommerce.EcommerceApplication"  "$@"
