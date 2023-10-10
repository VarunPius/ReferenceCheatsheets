# Start Your Journey with Apache Spark — Part 2
This document covers Process structured data using Spark SQL/DataFrame

Logically, DataFrames are similar to relational tables or DataFrames in Python/R with lots of optimizations behind the scene.
There are various ways we can create DataFrames from collections, HIVE tables, Relational tables, and RDDs.

Similar to the `sc` (Spark Context) in RDDs, we have a **SparkSession** which is the starting point for the DataFrames API.

When we launch `spark-shell` or `pyspark`, a SparkSession will be available as `spark`.
Otherwise, we can create one as per the below code.
```
spark = SparkSession.builder.master("local").getOrCreate()
```

Create a DataFrame based on a HIVE table
```
df = spark.table("tbl_name")
```

# Basic operations on DataFrames
- Count the number of rows
    ```
    df.count()
    ```

- Access the names of columns in the DataFrame
    ```
    df.columns
    ```

- Access the DataType of columns within the DataFrame
    ```
    df.dtypes
    ```

- Check how Spark stores the schema of the DataFrame
    ```
    df.schema
    ```

- Print the schema of the DataFrame in a heirarchical manner
    ```
    df.printSchema()
    ```

- Display the contents of the DataFrame.
    ```
    df.show()
    ```
    (By default the result will only display 20 records, however, you can specify the number of records to be displayed as the first argument.)

- Select particular columns from the DataFrame
    ```
    df.select("col1", "col2")
    ```

- Filter the rows based on some condition.
    Let’s try to find the rows with id = 1.
    There are different ways to specify the condition.
    ```
    from pyspark.sql.functions import col
    df.filter(df["id"] == 1)
    df.filter(df.id == 1)
    df.filter(col("id") == 1) 
    df.filter("id = 1") 
    ```
    Note: In order to use the "col" function, we need to import it.

- Drop a particular Column
    ```
    newdf = df.drop("id")
    ```
    Note: This operation will not drop the column from the "df" DataFrame because DataFrames are immutable in nature. However, it will return a new copy of the DataFrame without that column.

- Aggregations:
    We can use the groupBy function to group the data and then use the `agg` function to perform aggregation on grouped data.
    ```
    (df.groupBy("col1") \
        .agg(
            count("col2").alias("count"),
            sum("col2").alias("sum"),
            max("col2").alias("max"),
            min("col2").alias("min"),
            avg("col2").alias("avg")
            ).show()
    )
    ```

- Sorting: Sort the data based on "id". By default, sorting will be done in Ascending order.
    ```
    df.sort("id")
    ```
    
    - Sort the data in descending order.
        ```
        df.sort(desc("id")).show()
        ```

- Derived Columns
We can use the `withColumn` function to derive the column based on existing columns.
    ```
    df.withColumn("age", current_year — birth_year)
    ```
    where `age` is a derived column based on the expression `current_year — birth_year`.


# Joins
We can perform various types of joins on multiple DataFrames.

For example, let’s try to join 2 DataFrames `df1` and `df2` based on "id" column.
```
df1.join(df2, df1["id"] == df2["id"])
```

By default an inner join will be performed.
However, we can perform various other joins like `left_outer`, `right_outer`, `full_outer` etc. by passing these as the third argument.

For example, for left outer join we can say
```
df1.join(df2, df1["id"] == df2["id"], "left_outer")
```

# Executing SQL like queries
We can perform data analysis by writing SQL like queries as well.

In order to perform the SQL like queries, we need to register the DataFrame as a Temporary View.
```
df.createOrReplaceTempView("temp_table")
```

Now we can execute the SQL like queries as below :
```
spark.sql("select * from temp_table where id = 1").show()
```

- Saving the DataFrame as a HIVE Table
    ```
    df.write.saveAsTable("DB_NAME.TBL_NAME")
    ```

- We can also select the `mode` argument for `overwrite`, `append`, `error` etc.
    For example, if we want to overwrite the existing HIVE table we can use :
    ```
    df.write.saveAsTable("DB_NAME.TBL_NAME", mode="overwrite")
    ```

> Note: By default, the operation will save the DataFrame as a HIVE Managed table

- Saving the DataFrame as a HIVE External table
    ```
    df.write.saveAsTable("DB_NAME.TBL_NAME", path=<location_of_external_table>)
    ```

- Create a DataFrame from CSV file
    We can create a DataFrame using a CSV file and can specify various options like a separator, header, schema, inferSchema, and various other options.
    Let’s say we have CSV file delimited by `|` which has a header in it, and we would like to generate the schema automatically.

    ```
    df = spark.read.csv("path_to_csv_file", sep="|", header=True, inferSchema=True)
    ```
    
- Save a DataFrame as a CSV file
    Now let’s say we need to save the DataFrame back to a CSV file after performing our analysis, we can do the following:
    ```
    df.write.csv("path_to_CSV_File", sep="|", header=True, mode="overwrite")
    ```

- Create a DataFrame from a relational table
    We can read the data from relational databases using a JDBC URL.
    ```
    relational_df = spark.read.format(‘jdbc’).options(url=jdbc_url,dbtable= <TBL_NAME>,user= <USER_NAME>,password = <PASSWORD>).load()
    ```

- Save the DataFrame as a relational table
    We can save the DataFrame as a relational table using a JDBC URL.
    ```
    relational_df.write.format(‘jdbc’).options(url=jdbc_url,dbtable= <TBL_NAME>,user= <USER_NAME>,password = <PASSWORD>).mode(‘overwrite’).save()
    ```