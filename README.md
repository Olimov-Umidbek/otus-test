VM Arguments for program: -Xms128m -Xmx128m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/Users/alex/dump_find_all.hprof"

Create users in parallel
```bash
i = 0
while [[ i -lt 1000000 ]];
do
  ((i++));
  
  if [ "$(( i % 5 ))" -eq 0 ]; then
    echo "Create $i-th userEntity"
    sleep 0.1
  fi

  body="{\"username\":\"userEntity$i\", \"password\":\"pass$i\"}"
  curl -X POST -H "Content-Type: application/json" -d "$body" http://localhost:8080/users  > /dev/null 2>&1 &
done
```

Authenticate users
```bash
i = 0
while [[ i -lt 10000000 ]];
do
  ((i++));
  
  if [ "$(( i % 5 ))" -eq 0 ]; then
    echo "Query $i-th userEntity"
    sleep 0.1
  fi

  curl -X GET http://localhost:8080/users/current -u "userEntity$i:pass$i" > /dev/null 2>&1 &
done
```