#!/bin/bash
prefix='registry.cn-beijing.aliyuncs.com/letec/'
modules=(auth authmore)
dirs=(authmore-admin authmore-platform)
i=1
for m in "${modules[@]}"; do
    img_name=$prefix$m
    dir=${dirs[i]}
    ( cd $dir && docker build -f Dockerfile.test -t $img_name . && docker push $img_name )
    i=$((i+1))
done