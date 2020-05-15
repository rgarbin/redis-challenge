# Redis

An implementation of a subset of [Redis](http://redis.io/) features using Java

Challenge: [EngineeringRedisChallenge-1.pdf](EngineeringRedisChallenge-1.pdf).

To run:
```bash
$ make test
$ make run

curl -d 'VALUE' -X POST "http://localhost:8080/cmd/KEY"
curl -X GET "http://localhost:8080/cmd?key=KEY"    
curl -X GET "http://localhost:8080/cmd/dbsize"    
curl -X GET "http://localhost:8080/cmd/incr/KEY"    
curl -X DELETE "http://localhost:8080/cmd/KEY"
```


