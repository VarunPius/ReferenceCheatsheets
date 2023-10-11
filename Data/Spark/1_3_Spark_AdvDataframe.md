# Start your Journey with Apache Spark — Part 3
This document covers Advanced Spark DataFrame Operations and Catalog API

# BroadCast Join
Let’s say we want to join two DataFrames where one of the DataFrames is small.
We can use broadcast join to broadcast the small table.
This will reduce the shuffling of the data drastically and improve the performance of joins.

By default, the size of the broadcast table is 10 MB.
However, we can change the threshold up to 8GB as per the official documentation of Spark 2.3.

We can check the size of the broadcast table as follow :
```
spark.conf.get("spark.sql.autoBroadcastJoinThreshold")
```

Also, we can set the size of the broadcast table to 50 MB as follows :
```
spark.conf.set("spark.sql.autoBroadcastJoinThreshold", 50 * 1024 * 1024)
```

Here, for example, is a code snippet to join `big_df` and `small_df` based on `id`` column and where we would like to broadcast the `small_df`.
```python
from pyspark.sql.functions import broadcast
join_df = big_df.join(broadcast(small_df), big_df["id"] == small_df["id"])
```

# Caching
We can use the `cache()` or `persist()` function to keep the DataFrame in-memory.
It may improve the performance of your spark application significantly if you cache the data which you need to use frequently in your application.
However, over-caching can degrade performance. We have options to cache our data.
```
df.cache()
```

When we use the `cache()` function it will use storage level as `Memory_Only` until Spark 2.0.2. From Spark 2.1.x it is `Memory_and_DISK`.

However, if we need to specify the various available storage levels, then we can use the `persist()` method.
For example, if we need to keep the data in-memory only, we can use this snippet.
```
from pyspark.storagelevel import StorageLevel
```

Ref: Storage Level: https://spark.apache.org/docs/latest/rdd-programming-guide.html#rdd-persistence

- Selecting the storage level:
```
customer.persist(StorageLevel.MEMORY_ONLY)
```

It is also important to un-cache the data when it will no longer be required.

- Un-persisting a table:
```
df.unpersist()
```

In order to remove all cached tables we can use :
```
sqlContext.clearCache()
```

# SQL Expressions
We can use SQL expression for the manipulation of data as well.
We have `expr` function and also a variant of a select method as `selectExpr` for evaluation of SQL expressions.

Let’s try to find the `Quarter` from the date using a SQL expression.

- Using `expr` function
    ```
    import pyspark.sql.functions import expr
    cond = """case when month > 9 then ‘Q4’
                else case when month > 6 then ‘Q3’
                        else case when month > 3 then ‘Q2’
                            else case when month > 0 then ‘Q1’
                                end
                            end
                        end
                    end as quarter"""
    newdf = df.withColumn("quarter", expr(cond))
    ```
    We will get a new column as `quarter` which will give us the Quarter for the given month in `newdf`.

- Using `selectExpr` function
```
cond = """case when month > 9 then ‘Q4’
               else case when month > 6 then ‘Q3’
                    else case when month > 3 then ‘Q2’
                         else case when month > 0 then ‘Q1’
                              end
                         end
                     end
           end as quarter"""
newdf = df.selectExpr("*", cond)
```


# User-Defined Functions (UDF)
Often we need to write a function based on our specific requirement.
Here we can leverage UDFs. We can write our own functions in a language like Python and register the function as UDF, then we can use the function for DataFrame operations.

A Python function to find the Quarter for a given month:
```python
def detQuarter(mon):
    Q = None
    if(mon > 9):
        Q = ‘Q4’
    elif(mon > 6):
        Q = ‘Q3’
    elif(mon > 3):
        Q = ‘Q2’
    elif(mon > 0):
        Q = ‘Q1’
    return Q
```

Registering the function `detQuarter` as UDF:
```
quarter = udf(detQuarter, StringType())
```

Applying the function to determine the quarter for a month:
```
newdf = df.withColumn("Quarter", quarter("month"))
```

# Working with NULL Values
NULL values are always tricky to deal with irrespective of the Framework or language we use.
Here in Spark, we have a few specific functions to deal with NULL values.

- isNull()
    This function will help us to find the null values for any given columns. For example, if we need to find the columns where id columns contain the null values.
    ```
    newdf = df.filter(df["id"].isNull())
    ```
    Now in newdf DataFrame, we will have all records where the value of the id column is Null.

- isNotNull()
    This function works opposite to isNull() function and will return all the not null values for a particular function.
    ```
    newdf = df.filter(df["id"].isNotNull())
    ```
    Now in newdf DataFrame, we will have all records where the value of the `id` column is not Null.

- fillna()
    This function will help us to replace Null values.
    ```
    newdf = df.fillna(-1, ["col1", "col2"])
    ```
    Now above statement will replace all null values for columns `col1` and `col2` to -1. Similarly, we can use `fillna()` in a chained fashion to replace null values in another column with some different values.

- dropna()
    This function will help us to remove the rows with null values.
    ```
    # Remove all rows which contain any null values.
    newdf = df.dropna()
    # Remove all rows which contain all null values.
    newdf = df.dropna(how="all")
    ```
   
    > Note: the default value of the `how` param is `any`.

    ```
    # Remove all rows where column month is null.
    newdf = df.dropna(subset="month")
    ```


# Partitioning
Partitioning is a very important aspect to control the parallelism for spark Application.
By default, the number of partitions for Spark SQL is 200.
However, this can be controlled as well.

- Check the number of partitions
    ```
    df.rdd.getNumPartitions()
    ```

- Increase the number of partitions, e.g. increase partitions to 500
    ```
    newdf = df.repartition(500)
    ```
    > Note: This is an expensive operation since it requires shuffling of data across workers.

- Decrease the number of partitions, e.g. decrease partitions to 2.
    ```
    newdf = df.coalesce(2)
    ```

- Set the number of partitions at the Spark application level, e.g. to 500
    ```
    spark.conf.set("spark.sql.shuffle.partitions", "500")
    ```


# Catalog API
Spark Catalog is a user-facing API, which you can access using `SparkSession.catalog`.

Here I will demonstrate some of the basic functions which could be helpful while exploring a dataset.

- Get all databases:
    ```
    spark.catalog.listDatabases()
    ```
    This will return all the databases along with their location on the file system.

- Get all tables within a database:
    ```
    spark.catalog.listTables("DB_NAME")
    ```
    This will return all the tables for a given database along with information like table type (External/Managed) and whether a particular table is temporary or permanent.

- List columns for a particular table:
    ```
    spark.catalog.listColumns("TBL_NAME", "DB_NAME")
    ```
    This will return all the columns for a particular table in the database.
    It will also return the data type and whether the column is used in Partitioning or Bucketing.

- List all available functions you can use in the Spark session:
    ```
    spark.catalog.listFunctions()
    ```
    This will return all the available functions in the Spark session along with the information whether each is temporary or not.

- Get the current database:
    ```
    spark.catalog.currentDatabase()
    ```

- Set the current database:
    ```
    spark.catalog.setCurrentDatabase("DB_NAME")
    ```

- Cache a particular table:
    ```
    spark.catalog.cacheTable("DB_NAME.TBL_NAME")
    ```

- Check if a table is cached or not:
    ```
    spark.catalog.isCached("DB_NAME.TBL_NAME")
    ```
    This will help us to check programmatically whether a particular table is cached or not.

- Un-cache a table:
    ```
    spark.catalog.uncacheTable("DB_NAME.TBL_NAME")
    ```

- Un-cache all tables in the Spark session:
    ```
    spark.catalog.clearCache()
    ```
