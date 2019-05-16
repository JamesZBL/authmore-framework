#!/bin/bash
ssh root@$SERVER_HOST '(cd repo/authmore && git pull && ./gradlew build --scan -s -x test dockerBuildImage) \
    && docker stack deploy --with-registry-auth -c repo/authmore.preview.yml authmore'
