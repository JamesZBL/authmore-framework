#!/bin/bash
#prefix='registry.cn-beijing.aliyuncs.com/letec/'
#modules=(authmore-admin authmore-platform)
#i=1
#for m in "${modules[@]}"; do
#    img_name=$prefix$m
#    ( cd $m && docker build -f Dockerfile.test -t $img_name . && docker push $img_name )
#done
./gradlew dockerBuildImage dockerPushImage