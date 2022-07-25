# Docker Compose
Docker Compose file is a way to orchestrate multiple docker containers. It is usefule for multi-container applications. With Compose, you use a YAML file to configure your application’s services. Then, with a single command, you create and start all the services from your configuration.

Here is how an example `Docker Compose` file will look like. The file will create a persistent MySQL database so that we can reuse data:

```
version: '3.9'
services:
  mysql:
    container_name: mysql-db    #$$$
    image: mysql:8
    environment:
      MYSQL_DATABASE: 'your_database'
      MYSQL_USER: 'daniel'
      MYSQL_PASSWORD: 'daniel'
      MYSQL_ROOT_PASSWORD: 'root'
    ports:
      - "3306:3306"     # <Port exposed> : < MySQL Port running inside container>
    volumes:
      - mysql_volume:/var/lib/mysql
    networks:
      - backend
    restart:                    #$$$
      on-failure

volumes:
  mysql_volume:

networks:
  backend: 
```


# `services` commands
I will explain some of the commands with a compose file using the above as an example. In this section, we will explore commands with the `services` section. `services` is used to create multiple services. In the above example we used `mysql` to start a MySQL container. We can add more services too as can be seen in examples later. 

## `version`
In this file we will use version 3.9 compose. See docs

## `image`
We use `image` when we want to pull an existing image from repository. In the above example, it will pull MySQL version 8 from the default repository. The default repository is Docker. There is an option to pull from private repo too as follow:
```
image: registry.gitlab.com/project-or-group/project-name/image-name:tag
```

## `build`
This is not mentioned in the above example because we are using pre-built image. But this tag is used to build containers from the images we have written. 

Here's 2 examples of `build` tag inside you compose file:
```
services:
  web:
    build: .
    # other sections
  db:  
    build:
      context: .
      dockerfile: src/docker/Dockerfile-mysql
  ...
```

Here's the difference in the different build approaches:
- In the above section, the `web` service is built from the same location as the Docker compose file. It will search for the `DOCKERFILE` in that location, build it and start the container.
- For `db`, it will search for the `DOCKERFILE` at a custom location and build it. `build > context` is relative to `docker-compose` command running directory. `build > dockerfile` is relative to `build > context` directory. 

If assumption is the directory structure is as follows:
```
  main/
    |--docker-compose.yml
    |--src/
        |--docker/
              |--Dockerfile-mysql
```

The `context` in `build` sets the location to the `docker-compose.yml` location, i.e inside the `main` directory. The `build` will then take the `DOCKERFILE` at `dockerfile` and execute the commands inside it as though it's at `main` (and not `src/docker/`). So commands such as `COPY . .` inside `Dockerfile-mysql` will copy everything from `main` and not `src/docker/`. This is because `context` sets the location from where execution is to be conducted. This is important because Docker lets you have Docker CLI and Docker engine in 2 separate location and also let's you execute Dockerfiles from remote repositories.

## `environment`
This is where we will set environment variables. In this example, it is the environment variable needed to configure database and access. We will create database with name `your_database`, then we will create an user with name `daniel` and password `daniel` and also configure superuser account password with `root`. Identify environment variables for individual images that are needed.

## `ports`
This port will be used to map your container host to your host port (in this case, your linux server port). The mapping is `<host_port>:<container_port>`. If the `ports` value is `3307:3306`, `3307` is the one where we will access the database, while from inside the container, `3306` will be where the container will latch. 

## `volumes`
we will store persist mysql data from container filesystem `/var/lib/mysql`. So, whenever mysql container is restarted or stopped, the data won’t be erased.

## `networks`
This section defines networks. Suppose you start 2 different services, with 2 different network names, then these services can't talk to each other. An example would be:
```
services:
  web:
    networks:
      - backend
  db:  
    networks:
      - backend

networks:
  backend: 
```

Since the above 2 services are in `backend` network, `web` service can call `db` directly. The `host` for database in the `web` service would be `db` rather than `localhost` or reference it with the IP address. 
  
We can have multiple networks for same service:
```
services:
  web:
    networks:
      - backend
      - frontend
```

In this case, the we can create 2 more services, `db` in `backend` network and `nginx` in `frontend` network. In that case, `nginx` can't access `db` but `web` can access both `backend` and `frontend`. This segregation is useful.

## `depends_on`
Express dependency between services. Service dependencies cause the following behaviors:
- `docker-compose up` starts services in dependency order. In the following example, `db` and `redis` are started before `web`.
- `docker-compose up SERVICE` automatically includes `SERVICE`’s dependencies. In the example below, `docker-compose up web` also creates and starts `db` and `redis`.
- `docker-compose stop` stops services in dependency order. In the following example, `web` is stopped before `db` and `redis`.

  Simple example:
  ```
  version: "3.9"
  services:
    web:
      build: .
      depends_on:
        - db
        - redis
    redis:
      image: redis
    db:
      image: postgres
  ```


# `volumes` commands

# `networks` commands


# Running the Docker compose file
## Starting the service
Start with the following commands in the same directory as `docker-compose.yml`. If you want to run your services in the background, you can pass the `-d` flag (for **detached** mode) to `docker-compose up` 
```
docker-compose up -d
```
Reason you want to run the containers in detached mode is because you can do more things in the same terminal tab.

When we have to rebuild the containers, we use the following option:
```
docker-compose up --build
``` 
The above command lets you build your images and start the containers. The `--build` tag searches and reads the build section in each Docker Compose file and will execute it only when the images are to be built into containers. Once conatiners are deployed, we don't need to run it with the `--build` tag and can simply run `docker-compose up`

If the file is not named `docker-compose.yml` then we can give the `-f` to include the modified file name:
```
docker-compose -f docker-compose1.yml -f docker-compose2.yml up --build
```
Docker-compose will merge the multiple files together.

#$$$
```
docker compose up -d --no-deps --build mydocker
```
#$$$

## Check running containers
Use `docker-compose ps` to see what is currently running
```
$ docker-compose ps

       Name                      Command               State           Ports         
-------------------------------------------------------------------------------------
composetest_redis_1   docker-entrypoint.sh redis ...   Up      6379/tcp              
composetest_web_1     flask run                        Up      0.0.0.0:8000->5000/tcp
```

## Run commands inside your containers
The `docker-compose run` command allows you to run one-off commands for your services. For example, to see what environment variables are available to the `web` service:
```
docker-compose run web env
```

## Stopping services
If you started Compose with `docker-compose up -d`, stop your services once you’ve finished with them:
```
docker-compose stop
```

## Removing containers
You can bring everything down, removing the containers entirely, with the `down` command. Pass `--volumes` to also remove the data volume used by any container:
```
docker-compose down --volumes
```

> **Note**:
> Remember, we can create a image with a Dockerfile and use that image in the `services` section of the `docker-compose.yml`. Docker Compose services is not limited to existing ones. 

