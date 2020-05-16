# Redis

An implementation of a subset of [Redis](http://redis.io/) features using Java 8 with Spring-Shell + Spring-Web

Challenge: [EngineeringRedisChallenge-1.pdf](EngineeringRedisChallenge-1.pdf).

To run:
```bash
$ make test
$ make run

curl -d 'VALUE' -X POST "http://localhost:8080/cmd/KEY"
curl -X GET "http://localhost:8080/cmd?key=KEY"    
curl -X GET "http://localhost:8080/cmd/dbsize"    
curl -X PUT "http://localhost:8080/cmd/incr/KEY"
curl -X DELETE "http://localhost:8080/cmd/KEY"
curl -d '{"Rafael": 1, "Luis": 2}' -H "Accept: application/json" -H "Content-Type: application/json" -X POST "http://localhost:8080/cmd/zadd/SET"
curl -X GET "http://localhost:8080/cmd/zcard/SET"
curl -X GET "http://localhost:8080/cmd/zrank/SET/KEY"
curl -X GET "http://localhost:8080/cmd/zrange/SET?start=1&stop=2&withScore=false"
```


