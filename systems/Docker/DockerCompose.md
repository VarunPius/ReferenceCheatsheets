# Docker Compose
Docker Compose file is a way to orchestrate multiple docker containers. It is usefule for multi-container applications. With Compose, you use a YAML file to configure your application’s services. Then, with a single command, you create and start all the services from your configuration.

Here is how an example `Docker Compose` file will look like. The file will create a persistent MySQL database so that we can reuse data:

```
version: '3.9'
services:
  mysql:
    container_name: mysql-db    # //TODO
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
    restart:                    # //TODO
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
This section defines networks. Suppose you start 2 different services, with 2 different network names, then these services can't talk to each other. Since version 2, Docker has deprecated `links` and networks has been used. An example would be:
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
`volumes` is used to define the volumes or let the container know which volume to use if already created.

## Defining volumes
```
version: "3.2"
services:
  web:
    image: nginx:latest
    ports:
      - 8080:80
    volumes:
      - html_files:/usr/share/nginx/html
 
volumes:
  html_files:
    external: true
  redis_files:
    driver: local
  # Can be empty; volume will be created with default values
  mysql_files:
```
In the above example, we have used an external volume for `html_files`, local volume for `redis_files` and `mysql_files`.

## Types of volume mounts:
Two types of volume management exists in Docker: volumes and bind mounts.
- Bind mounts exist on the host file system and being managed by the host maintainer.
  Applications / processes outside of Docker can also modify it.

- Volumes can also be implemented on the host, but Docker will manage them for us and they can not be accessed outside of Docker.

Volumes are a much wider solution. Although both solutions help us to separate the data lifecycle from containers, by using Volumes you gain much more power and flexibility over your system.

With Volumes we can design our data effectively and decouple it from the host and other parts of the system by storing it dedicated remote locations (Cloud for example) and integrate it with external services like backups, monitoring, encryption and hardware management.

More Volumes advantages over bind mounts:
- No host concerns.
- Can be managed using Docker CLI.
Volumes can save you some uid/gid issues related permissions which occur in cases like when a container user's uid does not match the host `gid`.
- A new volume’s contents can be pre-populated by a container.

Here's 2 examples of each case:

### Case 1: Bind Mounts - Web server.
We want to provide our web server a configuration file that might change frequently.
For example: exposing ports according to the current environment.
We can rebuild the image each time with the relevant setup or create 2 different images for each environment.
Both of this solutions aren’t very efficient.

With **Bind mounts** Docker mounts the given source directory into a location inside the container.
(The original directory / file in the read-only layer inside the union file system will simply be overridden).

For example - binding a dynamic port to nginx:
```yaml
version: "3.7"
services:
  web:
    image: nginx:alpine
    volumes:
     - type: bind #<-----Notice the type
       source: ./mysite.template
       target: /etc/nginx/conf.d/mysite.template
    ports:
     - "9090:8080"
    environment:
     - PORT=8080
    command: /bin/sh -c "envsubst < /etc/nginx/conf.d/mysite.template > 
        /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"
```
> Notice that this example could also be solved using Volumes.

### Case 2 : Volumes - Databases.
Docker containers do not store persistent data: any data that will be written to the writable layer in container’s union file system will be lost once the container stop running.

But what if we have a database running on a container, and the container stops - that means that all the data will be lost?

Volumes to the rescue.
Those are named file system trees which are managed for us by Docker.

For example - persisting Postgres SQL data:
```
services:    
  db:
    image: postgres:latest
    volumes:
      - "dbdata:/var/lib/postgresql/data"
    volumes:
     - type: volume #<-----Notice the type
       source: dbdata
       target: /var/lib/postgresql/data
volumes:
  dbdata:
```

Notice that in this case, for named volumes, the source is the name of the volume (For anonymous volumes, this field is omitted).


How to identify reading a `docker-compose.yml` file whether it's a volumes or a bind mounts?
There are various ways you can configure this, such as:
- `./public:/usr/share/nginx/html`
- `/var/lib/postgresql/data`
- `/some/content:/usr/share/nginx/html`
- `~/configs:/etc/configs`
- `postgresql:/var/lib/postgresql/data`

The volume configuration has a short syntax format that is defined as:
```
[SOURCE:]TARGET[:MODE]
```

The different variations are essentially three unique forms:

**Type 1: No SOURCE**: eg. `/var/lib/postgresql/data`:
  When only a target is specified, without a source, Docker Compose will create an anonymous directory and mount it as a volume to the target path inside the container.
  The directory's path on the host system is by default /var/lib/docker/volumes/<uuid>/_data, where <uuid> is a random ID assigned to the volume as its name.

**Type 2: A non-path SOURCE**: eg. `postgresql-data:/var/lib/postgresql/data`:
  If a source is present and it's not a path, then Docker Compose assumes you're referring to a named volume. This volume needs to be declared in the same file in the top-level `volumes` key declaration (**top level** meaning as in outermost in yaml file. Such as: `version`, `services`, etc.).

  Top-level `volumes` key always declares volumes, never bind mounts. Bind mounts don't have a name and they can't be named.

**Type 3: A path SOURCE**: eg. `/some/content:/usr/share/nginx/html or ./public:/usr/share/nginx/html`:
  If source is a path, absolute or relative, Docker Compose will bind mount the folder into the container. Relative paths starting with `.` or `..` are relative to the location of `docker-compose.yml`.


# `networks` commands
Running the command `docker network ls` will list out your current Docker networks; it should look similar to the following:
```
$ docker network ls
NETWORK ID          NAME                         DRIVER
17cc61328fef        bridge                       bridge
098520f7fce0        composedjango_default        bridge
1ce3c572afc6        composeflask_default         bridge
8fd07d456e6c        host                         host
3b578b919641        none                         null
```

## Defining networks
Specify your own networks with the top-level `networks` key, to allow creating more complex topologies and specify network drivers (and options). You can also use this configuration to connect services with external networks Docker Compose does not manage. Each service can specify which networks to connect to with its `service`-level `networks` key.

The following example defines two custom networks. Keep in mind, `proxy` cannot connect to `db`, as they do not share a network; however, `app` can connect to both. In the `front` network, we specify the IPv4 and IPv6 addresses to use (we have to configure an `ipam` block defining the subnet and gateway configurations). We could customize either network or neither one, but we do want to use separate drivers to separate the networks (review Basic Networking with Docker for a refresher):
```
version: '2'

services:
    proxy:
        build: ./proxy
        networks: 
            - front
    app:
        build: ./app
        networks:
            # you may set custom IP addresses
            front:
                ipv4_address: 172.16.238.10 
                ipv6_address: "2001:3984:3989::10"
            - back
    db:
        image: postgres
        networks:
            - back

networks:
    front:
        # use the bridge driver, but enable IPv6
        driver: bridge
        driver_opts:
            com.docker.network.enable_ipv6: "true"
        ipam:
            driver: default
            config:
                - subnet: 172.16.238.0/24
                gateway: 172.16.238.1
                - subnet: "2001:3984:3989::/64"
                gateway: "2001:3984:3989::1"
    back:
        # use a custom driver, with no options
        driver: custom-driver-1
    
    # leaving it blank will take default values, which is the bridge network
    test:     
```

## Pre-Existing Networks
You can even use pre-existing networks with Docker Compose; just use the external option:
```
version: '2'

networks:
    default:
        external:
            name: i-already-created-this
```

In this case, Docker Compose never creates the default network; instead connecting the app’s containers to the `i-already-created-this` network.



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

//TODO
```
docker compose up -d --no-deps --build mydocker
```


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

## Starting only specific services
Consider you have 3 different services, `web`, `db` and `cache` in a Docker compose file. If you want to start only part of the services, you do as follows:
```
docker compose up web db
docker compose up db
docker compose up cache
```

## Scaling services
Consider you have 3 different services, `web`, `db` and `cache` in a Docker compose file. If you want to start multiple instances of part of the services, you do as follows:
```
docker compose up -d --scale web=3
```