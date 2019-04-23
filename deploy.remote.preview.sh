#!/bin/bash
echo $SSH_PRIVATE_KEY > ~/.ssh/key
ssh -o StrictHostKeyChecking=no root@$SERVER_HOST -i ~/.ssh/key 'docker stack deploy --with-registry-auth \
    -c repo/authmore.preview.yml authmore'