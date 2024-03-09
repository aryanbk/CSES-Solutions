#!/bin/bash

# cd /path/of/repository

git add .

# changes=$(git diff --name-only --cached)
changes=$(git diff --name-only --cached | grep -v '/\..*')


commit_msg="added "
for file in $changes; do
    commit_msg+="$(basename $file), "
done

commit_msg=${commit_msg::-2}

commit_msg+=" and updated "
for file in $changes; do
    commit_msg+="$(basename $file), "
done
commit_msg=${commit_msg::-2}

git commit -m "$commit_msg"

git push origin main