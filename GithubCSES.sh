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

if [ $? -eq 0 ]; then
    echo "Commit successful!"
else
    echo "Error: Commit failed."
    exit 1
fi

git push origin main

if [ $? -eq 0 ]; then
    echo "Push successful!"
else
    echo "Error: Push failed."
    exit 1
fi

echo "Commit message: $commit_msg"