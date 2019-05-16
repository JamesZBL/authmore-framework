#!/bin/bash
mkdir -p archive
fileNamePrefix="authmore-framework-"
for i in master dev; do
    git archive --format tar.gz --output archive/$fileNamePrefix$i.tar.gz $i
done
echo Export finished.