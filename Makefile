MVN_CMD				:= mvn -T 4C
PROJECT_NAME		?= $(shell basename `pwd` | tr '[:upper:]' '[:lower:]' | tr -d '[:punct:]')

.DEFAULT_GOAL := help

test: 
	@$(MVN_CMD) -Dtest=RedisServerTest test

build: 
	@$(MVN_CMD) clean install -U -DskipTests=true

run: build
	@$(MVN_CMD) spring-boot:run

.PHONY: build run test
