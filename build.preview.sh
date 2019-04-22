#!/bin/bash
./gradlew build -x test
prefix='registry.cn-beijing.aliyuncs.com/letec/'
modules=(auth authmore)
for m in "${modules[@]}"; do
    img_name=$prefix$m
    ( cd $m && docker build -f Dockerfile.test -t $img_name . && docker push $img_name )
done