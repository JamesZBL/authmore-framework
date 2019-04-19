#!/bin/bash
prefix='registry.cn-beijing.aliyuncs.com/letec/'
modules=(auth authmore)
for m in "${modules[@]}"; do
    img_name=$prefix$m
    ( cd $m && gradle bootJar && docker build -f Dockerfile.test -t $img_name . && docker push $img_name )
done