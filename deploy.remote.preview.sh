#!/bin/bash
echo $SSH_PRIVATE_KEY > ~/.ssh/key
ssh root@$SERVER_HOST -i ~/.ssh/key 'docker stack deploy --with-registry-auth -c repo/authmore.preview.yml authmore'