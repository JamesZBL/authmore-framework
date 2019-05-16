#!/bin/bash
home="archive"
mkdir -p $home
remote="git@gitee.com:zbl1996"
for i in master; do
    for t in zip; do
        git archive --prefix=authmore-ui/ --format=$t --output archive/authmore-ui-$i.$t --remote=$remote/authmore-ui.git $i
        git archive --prefix=authmore-framework/ --format=$t --output archive/authmore-framework-$i.$t --remote=$remote/authmore.git $i
        cd $home
        mkdir tmp
        for f in authmore-*-$i.$t; do
            unzip -d tmp -o -u $f && rm $f
        done
        (cd tmp && zip -o authmore.$t -r authmore.$t .)
        mv tmp/authmore.$t .
        rm -r tmp
    done
done
echo Export finished.