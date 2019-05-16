#!/bin/bash
home="archive"
mkdir -p $home
remote="git@gitee.com:zbl1996"
for i in master; do
    for t in zip; do
        git archive --format $t --output archive/authmore-framework-$i.$t --remote=$remote/authmore.git $i
        git archive --format $t --output archive/authmore-ui-$i.$t --remote=$remote/authmore-ui.git $i
        (cd $home && zip authmore.zip authmore-framework-$i.$t authmore-ui-$i.$t)
    done
done
echo Export finished.