# Kafka
<Introduction>

# Installation
## Mac: Using Homebrew
1. To install Kafka on Mac using Homebrew, open terminal and run the following commands:
    ```
    brew cask install java
    brew install kafka
    ```

    Here are the logs:
    ```
    ==> Installing dependencies for kafka: zookeeper
    ==> Installing kafka dependency: zookeeper
    ==> Downloading https://homebrew.bintray.com/bottles/zookeeper-3.4.12.high_sierra.bottle.tar.gz
    ######################################################################## 100.0%
    ==> Pouring zookeeper-3.4.12.high_sierra.bottle.tar.gz
    ==> Caveats
    To have launchd start zookeeper now and restart at login:
    brew services start zookeeper
    Or, if you don't want/need a background service you can just run:
    zkServer start
    ==> Summary
    🍺  /usr/local/Cellar/zookeeper/3.4.12: 242 files, 32.9MB
    ==> Installing kafka
    ==> Downloading https://homebrew.bintray.com/bottles/kafka-2.0.0.high_sierra.bottle.tar.gz
    ######################################################################## 100.0%
    ==> Pouring kafka-2.0.0.high_sierra.bottle.tar.gz
    ==> Caveats
    To have launchd start kafka now and restart at login:
    brew services start kafka
    Or, if you don't want/need a background service you can just run:
    zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties
    ==> Summary
    🍺  /usr/local/Cellar/kafka/2.0.0: 160 files, 46.8MB
    ==> Caveats
    ==> zookeeper
    To have launchd start zookeeper now and restart at login:
    brew services start zookeeper
    Or, if you don't want/need a background service you can just run:
    zkServer start
    ==> kafka
    To have launchd start kafka now and restart at login:
    brew services start kafka
    Or, if you don't want/need a background service you can just run:
    zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties
    ```

Once done, go to the subsequent section called `Starting Kafka`

## Linux/Unix
1. Get Kafka:
    ```
    $ tar -xzf kafka_2.13-3.2.1.tgz
    $ cd kafka_2.13-3.2.1
    ```

2. Start the Kafka environment:
    NOTE: Your local environment must have Java 8+ installed.
    Run the following commands in order to start all services in the correct order:
    ```sh
    # Start the ZooKeeper service
    # Note: Soon, ZooKeeper will no longer be required by Apache Kafka.
    $ bin/zookeeper-server-start.sh config/zookeeper.properties
    ```

3. Open another terminal session and run:
    ```sh
    # Start the Kafka broker service
    $ bin/kafka-server-start.sh config/server.properties
    ```

Once all services have successfully launched, you will have a basic Kafka environment running and ready to use. 


# Starting Kafka
1. Start Zookeeper:
    ```sh
    # if brew installation:
    zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
    # or 
    # if installation from source
    bin/zookeeper-server-start.sh config/zookeeper.properties
    ```

    You may be wondering what Zookeeper is and why do we require it. To put it simply, Zookeeper is a top-level software also maintained by Apache that acts as a centralized service used to maintain naming and configuration data. It provides a robust synchronization within distributed systems. Zookeeper keeps track of the status of the Kafka cluster nodes, Kafka topics, partitions, etc.

> In the future (soon) ZooKeeper will no longer be required by Apache Kafka, but in the meantime, we'll have to deal with it.Zookeeper

2. Start Kafka server:
    ```sh
    # if brew installation:
    kafka-server-start /usr/local/etc/kafka/server.properties
    # or
    # if installation from source
    bin/kafka-server-start.sh config/server.properties
    ```

> WARNING:
    > During server start, you might be facing connection broken issue.
    > ```
    > [2018-08-28 16:24:41,166] WARN [Controller id=0, targetBrokerId=0] Connection to node 0 could not be established. Broker may not be available. (org.apache.kafka.clients.NetworkClient)
    > [2018-08-28 16:24:41,268] WARN [Controller id=0, targetBrokerId=0] Connection to node 0 could not be established. Broker may not be available. (org.apache.kafka.clients.NetworkClient)
    > ```
    > 
    > To fix this issue, we need to change the server.properties file.
    > ```
    > vim /usr/local/etc/kafka/server.properties
    > ```
    > 
    > Here uncomment the server settings and update the value from
    > ```
    > listeners=PLAINTEXT://:9092
    > ```
    > 
    > to
    > 
    > ```
    > ############################# Socket Server Settings ############################## The address the socket server listens on. It will get the value returned from 
    > # java.net.InetAddress.getCanonicalHostName() if not configured.
    > #   FORMAT:
    > #     listeners = listener_name://host_name:port
    > #   EXAMPLE:
    > #     listeners = PLAINTEXT://your.host.name:9092
    > listeners=PLAINTEXT://localhost:9092
    > ```
    >    
    > and restart the server and it will work great.

3. Create Kafka Topic: A topic is a category or feed name to which records are published. Topics in Kafka are always multi-subscriber; that is, a topic can have zero, one, or many consumers that subscribe to the data written to it.
    ```sh
    # if brew installation:
    kafka-topics --create --zookeeper localhost:9092 --replication-factor 1 --partitions 1 --topic test
    # or
    # if installation from source
    bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
    ```

    Here we have created a topic name `test`. `9092` is the port configured for the Kafka Server to run at in step 2 (in the `server.properties` file)

4. Initialize Producer console: Now we will initialize the Kafka producer console, which will listen to `localhost` at port `9092` at topic `test`:
    ```sh
    # if brew installation:
    kafka-console-producer --broker-list localhost:9092 --topic test
    # or 
    # if installation from source
    bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092

    >send first message
    >send second message
    >wow it is working
    ```

5. Initialize Consumer console: Now we will initialize the Kafka consumer console, which will listen to bootstrap server `localhost` at port `9092` at topic `test` from beginning:
    ```sh
    # if brew installation:
    kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginningsend first message
    # or
    # if installation from source
    bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092

    send second message
    wow it is working
    ```

# Theory