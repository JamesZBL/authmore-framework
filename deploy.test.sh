#!/bin/sh
( cd auth && gradle bootJar )
( cd authmore && gradle bootJar )
docker-compose -f docker-compose.test.yml build
docker stack deploy -c docker-compose.test.yml authmore
