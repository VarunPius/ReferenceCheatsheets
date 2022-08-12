# Redis Docker
redis/redis-stack

# How to install Redis Stack using Docker
To get started with Redis Stack using Docker, you first need to select a Docker image:
- `redis/redis-stack` contains both Redis Stack server and RedisInsight. This container is best for local development because you can use the embedded RedisInsight to visualize your data.
- `redis/redis-stack-server` provides Redis Stack server only. This container is best for production deployment.

# Getting started
## redis/redis-stack-server
To start Redis Stack server using the `redis-stack-server` image, run the following command in your terminal:
```
docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server:latest
```

You can connect the Redis Stack server database to your RedisInsight desktop application.

## redis/redis-stack
To start Redis Stack developer container using the redis-stack image, run the following command in your terminal:
```
docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
```

The `docker run` command above also exposes RedisInsight on port 8001. You can use RedisInsight by pointing your browser to localhost:8001.

# Connect with redis-cli
You can then connect to the server using `redis-cli`, just as you connect to any Redis instance.

If you don’t have `redis-cli` installed locally, you can run it from the Docker container:
```
$ docker exec -it redis-stack redis-cli
```

# Configuration 
## Persistence
To persist your Redis data to a local path, specify `-v` to configure a local volume. This command stores all data in the local directory `local-data`:
```
$ docker run -v /local-data/:/data redis/redis-stack:latest
```

## Ports
If you want to expose Redis Stack server or RedisInsight on a different port, update the left hand of portion of the `-p` argument. This command exposes Redis Stack server on port 10001 and RedisInsight on port 13333:
```
$ docker run -p 10001:6379 -p 13333:8001 redis/redis-stack:latest
```

## Config files
By default, the Redis Stack Docker containers use internal configuration files for Redis. To start Redis with local configuration file, you can use the `-v` volume options:
```
$ docker run -v `pwd`/local-redis-stack.conf:/redis-stack.conf -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
```

## Environment variables
To pass in arbitrary configuration changes, you can set any of these environment variables:
- `REDIS_ARGS`: extra arguments for Redis
- `REDISEARCH_ARGS`: arguments for RediSearch
- `REDISJSON_ARGS`: arguments for RedisJSON
- `REDISGRAPH_ARGS`: arguments for RedisGraph
- `REDISTIMESERIES_ARGS`: arguments for RedisTimeSeries
- `REDISBLOOM_ARGS`: arguments for RedisBloom

For example, here's how to use the `REDIS_ARGS` environment variable to pass the requirepass directive to Redis:
```
docker run -e REDIS_ARGS="--requirepass redis-stack" redis/redis-stack:latest
```

Here's how to set a retention policy for RedisTimeSeries:
```
docker run -e REDISTIMESERIES_ARGS="RETENTION_POLICY=20" redis/redis-stack:latest
```

# Docker Compose file
Here is sample Redis docker compose file:
```
version: '3.8'
services:
  cache:
    image: redis:6.2-alpine
    restart: always
    ports:
      - '6379:6379'
    command: redis-server --save 20 1 --loglevel warning --requirepass eYVX7EwVmmxKPCDmwMtyKVge8oLd2t81
    volumes: 
      - cache:/data
volumes:
  cache:
    driver: local
```

Here in the above `docker-compose` file, we have defined a service called `cache`. The `cache` service will pull the `redis:6.2.alpine` image from Dockerhub. It is set to restart `always`, if the docker container fails for some reason it will restart. Then, we map the container port 6379 to the local port 6379. If we aim to run multiple verisons of Redis, we can choose a random port.

Consequently, we use a custom redis-server command with `--save 20 1` which instructs the server to save 1 or more writes every 20 seconds to disk in case the server restarts. We are using the `--requirepass` parameter to add authentication with the password to read/write data on the Redis server. As we know if this was a production-grade application the password won’t be exposed out. This is being done here because it is only intended for development purposes.

Subsequently, we use a volume for the `/data` where any writes will be persisted. It is mapped to a volume called `cache`. This volume is managed as a local driver.

If we run a docker-compose up with the above file using ​`​docker-compose -f docker-compose-redis-only.yml up` it will give an output like below:

