#!/bin/bash
ssh root@$SERVER_HOST '(cd repo/authmore && git pull -f && ./gradlew build --scan -s -x test dockerBuildImage dockerPushImage) \
    && docker stack deploy --with-registry-auth -c repo/authmore.preview.yml authmore'
