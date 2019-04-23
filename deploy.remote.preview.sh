#!/bin/bash
key_file=~/.ssh/key
echo $SSH_PRIVATE_KEY > $key_file
ssh-add $key_file
chmod 600 $key_file
ssh -o StrictHostKeyChecking=no root@$SERVER_HOST -i $key_file 'docker stack deploy --with-registry-auth \
    -c repo/authmore.preview.yml authmore'