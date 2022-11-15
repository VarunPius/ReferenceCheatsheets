# Kafka Single Node Cluster
## Start Kafka Broker
```
docker-compose up -d
```

## Create topic
```
docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic quickstart
```

## Write messages
```
docker exec --interactive --tty broker \
kafka-console-producer --bootstrap-server broker:9092 \
                       --topic quickstart
```

## Read messages
```
docker exec --interactive --tty broker \
kafka-console-consumer --bootstrap-server broker:9092 \
                       --topic quickstart \
                       --from-beginning
```

# Kafka Multiple Node Cluster
## Docker Configs
- `KAFKA_ZOOKEEPER_CONNECT`: Instructs Kafka how to get in touch with ZooKeeper.
- `KAFKA_ADVERTISED_LISTENERS`: variable is set to `localhost:29092`.
    This makes Kafka accessible from outside the container by advertising its location on the Docker host.
    Describes how the host name that is advertised and can be reached by clients.
    The value is published to ZooKeeper for clients to use.
    If using the SSL or SASL protocol, the endpoint value must specify the protocols in the following formats:
    ```
        SSL: SSL:// or SASL_SSL://
        SASL: SASL_PLAINTEXT:// or SASL_SSL://
    ```


## Create Topic
```
docker exec imagesnotes-kafka-1-1 kafka-topics --bootstrap-server localhost:19092 \
             --create \
             --topic quickstart

--v2
docker exec imagesnotes-kafka-1-1 kafka-topics --bootstrap-server kafka-1:19092 \
             --create \
             --topic quickstart
```
The `bootstrap-server` value here (`localhost:19091`) is from `KAFKA_ADVERTISED_LISTENERS` and not ports.
Imagine if in the Docker-compose file, you set it to `PLAINTEXT://localhost:19091` and in ports you set it to `19092:19091`, having `bootstrap-server` as `19092` won't work. It will be `19091`.

## Write message
```
docker exec --interactive --tty imagesnotes-kafka-2-1 \
kafka-console-producer --bootstrap-server localhost:29092 \
                       --topic quickstart

--v2
docker exec --interactive --tty imagesnotes-kafka-1-1 \
kafka-console-producer --bootstrap-server kafka-1:19092 \
                       --topic quickstart
```


## Read mesages
```
docker exec --interactive --tty imagesnotes-kafka-3-1 \
kafka-console-consumer --bootstrap-server localhost:39092 \
                       --topic quickstart \
                       --from-beginning

--v2
docker exec --interactive --tty imagesnotes-kafka-1-1 \
kafka-console-consumer --bootstrap-server kafka-3:39092 \
                       --topic quickstart \
                       --from-beginning
```

