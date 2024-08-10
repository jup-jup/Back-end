#!/bin/bash

# 8080 포트를 사용하는 프로세스의 PID 찾기
PID=$(lsof -ti:8080)

# PID가 존재하면, 해당 프로세스를 강제 종료
if [ ! -z "$PID" ]
then
  kill -9 $PID
  echo "Process $PID on port 8080 has been killed."
else
  echo "No process is using port 8080."
fi

