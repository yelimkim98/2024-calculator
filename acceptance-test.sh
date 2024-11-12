#!/bin/bash

# HTTP GET 요청을 보내고 결과를 변수에 저장
result=$(curl -s http://localhost:8765/sum?a=1&b=2)

# 결과가 3인지 검사
if test "$result" -eq 3; then
    echo "The result is 3."
else
    echo "The result is not 3. It is $result."
fi
