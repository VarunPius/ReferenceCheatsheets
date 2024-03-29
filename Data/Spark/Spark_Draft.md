x https://medium.com/expedia-group-tech/start-your-journey-with-apache-spark-part-1-3575b20ee088
x https://medium.com/expedia-group-tech/start-your-journey-with-apache-spark-part-2-682891efda4b
x https://medium.com/expedia-group-tech/start-your-journey-with-apache-spark-part-3-1ae77c05187

- https://medium.com/expedia-group-tech/deep-dive-into-apache-spark-array-functions-720b8fbfa729
- https://medium.com/expedia-group-tech/deep-dive-into-apache-spark-window-functions-7b4e39ad3c86
- https://medium.com/expedia-group-tech/deep-dive-into-apache-spark-datetime-functions-b66de737950a
- https://medium.com/expedia-group-tech/working-with-json-in-apache-spark-1ecf553c2a8c

- https://selectfrom.dev/apache-spark-coalesce-and-repartition-20ce793ae5d7
- https://selectfrom.dev/repartition-vs-coalesce-in-apache-spark-82726c7c7d66
- https://msdilli.medium.com/important-interview-point-on-coalesce-vs-repartition-performance-evaluation-9e15329aae7b
- https://medium.com/mercedes-benz-techinnovation-blog/increasing-apache-spark-read-performance-for-jdbc-connections-a028115e20cd
- https://joydipnath.medium.com/how-to-determine-executor-core-memory-and-size-for-a-spark-app-19310c60c0f7
- https://joydipnath.medium.com/garbage-collection-tuning-concepts-in-spark-cf1a784e83c
- https://nivedita-mondal.medium.com/spark-memory-management-oom-issues-16dcab6be375
- https://msdilli.medium.com/spark-optimization-techniques-681cb85d01f9
- https://medium.com/@sandhiya320/apache-spark-sql-partitioning-bucketing-c5831b8d166c
- https://medium.com/towardsdev/pyspark-broadcast-variables-and-accumulator-6addab4d8aff
- https://medium.com/@a.anekpattanakij/big-data-file-formats-introduction-to-arvo-parquet-and-orc-file-d153f0f20b1e
- https://nivedita-mondal.medium.com/spark-join-strategies-fb984b50441d

- https://medium.com/analytics-vidhya/sparksql-and-dataframe-high-level-api-basics-using-pyspark-eaba6acf944b
- https://nivedita-mondal.medium.com/spark-interview-guide-spark-core-2ab0f57304eb
- https://nivedita-mondal.medium.com/spark-interview-guide-part-2-rdd-7911519e68c1
- https://nivedita-mondal.medium.com/spark-interview-guide-part-3-spark-sql-dataframe-12f04f168d6b
- https://nivedita-mondal.medium.com/spark-interview-guide-module-4-spark-structured-streaming-af236380133c
- https://nivedita-mondal.medium.com/spark-interview-guide-part-4-important-concepts-22a464b693d0
- https://nivedita-mondal.medium.com/spark-optimization-technique-how-to-improve-performance-for-your-spark-application-f75dc58b6b86
- https://medium.com/towards-data-science/apache-spark-optimization-techniques-fa7f20a9a2cf


— Use DataFrame/Dataset over RDD
— Use mapPartitions() over map()
— Use coalesce() over repartition()
— Avoid UDF’s (User Defined Functions)
— Use Serialized data format’s
— Caching data in memory
— Reduce expensive Shuffle operations
— Disable DEBUG & INFO Logging

https://blog.devops.dev/a-comprehensive-guide-on-apache-hive-basics-582cc07fe124
https://medium.com/@vdtdg/advice-for-devs-who-want-to-build-strong-etls-a32a25225a08
https://medium.com/nerd-for-tech/joining-strategies-in-apache-spark-f802a7dab150
https://www.youtube.com/playlist?list=PLmtsMNDRU0Bw6VnJ2iixEwxmOZNT7GDoC

# Dealing with skewed data in partitions
When dealing with skewed data in Spark, one technique is to use partitioning to optimize performance. According to Towards Data Science, you can use the following methods to diagnose if your data are skewed:

import pyspark.sql.functions as F
df.groupBy(F.spark_partition_id()).count().show()
This will show you how many rows are in each partition and help you identify which partitions are skewed.

Once you have identified the skewed partitions, you can use techniques such as bucketing or salting to redistribute the data more evenly across partitions.

- read parquet, avro and delta files
- write "
- joins:
    - types
    - anti join for not in
    - column names for join condition types
    - broadcast
- column selection