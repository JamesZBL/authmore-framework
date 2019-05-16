#!/bin/sh
( cd authmore-admin && gradle bootJar )
( cd authmore-platform && gradle bootJar )
docker-compose -f docker-compose.test.yml build
docker stack deploy -c docker-compose.test.yml authmore
