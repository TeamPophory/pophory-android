#!/bin/bash

# 현재 브랜치
current_branch=$(git rev-parse --abbrev-ref HEAD)

# 이전 릴리즈(master)에서부터 현재 브랜치까지의 커밋 로그를 가져옵니다
commitLogs=$(git log main..develop --pretty=format:"%h - %s (%an, %ar)" )

# 로그를 releaseNote.txt 파일에 저장합니다
echo "$commitLogs" > ./firebase/releaseNote.txt

echo "Release notes saved to releaseNote.txt"
