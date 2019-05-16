#!/bin/bash
mkdir -p archive
fileNamePrefix="authmore-framework-"
for i in master dev; do
    for t in tar.gz zip; do
        git archive --format $t --output archive/$fileNamePrefix$i.$t $i
    done
done
echo Export finished.