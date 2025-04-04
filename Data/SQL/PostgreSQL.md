# Postgres specific commands
```sql
-- Listing Databases
\l

-- Switching Databases
\c <dbname>

-- Listing Tables:
\dt

-- Quit psql console:
\q
```


# Working with JSON
Postgres supports both Arrays and JSON dictionary. PostgreSQL offers two data types for storing JSON:
  - JSON – store an exact copy of the JSON text.
  - JSONB – store the JSON data in binary format.

Assume following example:
```sql
CREATE TABLE products(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    properties JSONB
);
```

## Storing JSON objects example
Now let's insert some data:
```sql
INSERT INTO products(name, properties)
VALUES('Ink Fusion T-Shirt','{"color": "white", "size": ["S","M","L","XL"]}')
RETURNING *;

id |        name        |                    properties
----+--------------------+---------------------------------------------------
  1 | Ink Fusion T-Shirt | {"size": ["S", "M", "L", "XL"], "color": "white"}
(1 row)


INSERT INTO products(name, properties)
VALUES('ThreadVerse T-Shirt','{"color": "black", "size": ["S","M","L","XL"]}'),
      ('Design Dynamo T-Shirt','{"color": "blue", "size": ["S","M","L","XL"]}')
RETURNING *;

id |        name         |                    properties
----+---------------------+---------------------------------------------------
  2 | ThreadVerse T-Shirt | {"size": ["S", "M", "L", "XL"], "color": "white"}
  3 | Design Dynamo       | {"size": ["S", "M", "L", "XL"], "color": "blue"}
(2 rows)


SELECT id, name, properties
FROM products;

id |         name          |                    properties
----+-----------------------+---------------------------------------------------
  1 | Ink Fusion T-Shirt    | {"size": ["S", "M", "L", "XL"], "color": "white"}
  2 | ThreadVerse T-Shirt   | {"size": ["S", "M", "L", "XL"], "color": "black"}
  3 | Design Dynamo T-Shirt | {"size": ["S", "M", "L", "XL"], "color": "blue"}
(3 rows)
```

Retrieve color from properties column
```sql
SELECT
  id,
  name,
  properties -> 'color' color
FROM
  products;

Output:
id |         name          |  color
----+-----------------------+---------
  1 | Ink Fusion T-Shirt    | "white"
  2 | ThreadVerse T-Shirt   | "black"
  3 | Design Dynamo T-Shirt | "blue"
(3 rows)
```

The `->` operator extracts a JSON object field by a key. In this example, we use the `->` operator `->` to extract the color of the properties object:
```
properties -> 'color'
```

The values in the color column are surrounded by double quotes (`“`).

To extract a JSON object field by a key as text, you can use the `->>` operator. For example:
```sql
SELECT
  id,
  name,
  properties ->> 'color' color
FROM
  products;

id |         name          | color
----+-----------------------+-------
  1 | Ink Fusion T-Shirt    | white
  2 | ThreadVerse T-Shirt   | black
  3 | Design Dynamo T-Shirt | blue
(3 rows)
```

Next, retrieve the products with the colors black and white using the ->> operator in the WHERE clause:
```sql
SELECT
  id,
  name,
  properties ->> 'color' color
FROM
  products
WHERE
  properties ->> 'color' IN ('black', 'white');

id |        name         | color
----+---------------------+-------
  1 | Ink Fusion T-Shirt  | white
  2 | ThreadVerse T-Shirt | black
(2 rows)
```

## Storing JSON arrays example
Consider the following example:
```sql
CREATE TABLE contacts(
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   phones JSONB
);

INSERT INTO contacts(name, phones)
VALUES
   ('John Doe','["408-111-2222", "408-111-2223"]'),
   ('Jane Doe','["212-111-2222", "212-111-2223"]')
RETURNING *;

id |   name   |              phones
----+----------+----------------------------------
  1 | John Doe | ["408-111-2222", "408-111-2223"]
  2 | Jane Doe | ["212-111-2222", "212-111-2223"]
(2 rows)
```

Retrieve contacts with the work phone numbers from the contacts table:
```sql
SELECT
  name,
  phones ->> 0 "work phone"
FROM
  contacts;

name   |  work phone
----------+--------------
 John Doe | 408-111-2222
 Jane Doe | 212-111-2222
(2 rows)
```

The `->> index` extract the `index` element in an array. In this example, we use the `->> 0` to extract the first elements in the phones array as text.


# Advanced techniques for performance optimization
## Indexing strategies
Indexing is one of the most crucial aspects of database performance. PostgreSQL provides various indexing techniques, and understanding when and how to use them is essential.

### B-Tree Indexes
The default index type in PostgreSQL is the B-Tree index, which is suitable for most use cases. However, PostgreSQL also supports other index types like Hash, GiST (Generalized Search Tree), GIN (Generalized Inverted Index), and SP-GiST (Space-partitioned Generalized Search Tree). Knowing when to use each type can significantly impact query performance.

Example:
```sql
-- Creating a B-Tree Index
CREATE INDEX idx_users_email ON users(email);
```

### Partial Indexes
Partial indexes are used to index a subset of rows in a table. This can significantly reduce index size and improve query performance, especially for queries that filter on a specific condition.

Example:
```sql
-- Creating a Partial Index for Active Users
CREATE INDEX idx_active_users_email ON users(email) WHERE is_active = true;
```

## Query Optimization
PostgreSQL’s query planner is highly sophisticated, but you can optimize query performance further by understanding query execution plans, using appropriate join strategies, and avoiding common pitfalls like full table scans.

### EXPLAIN ANALYZE
The `EXPLAIN ANALYZE` command provides insights into how PostgreSQL executes a query. It helps identify slow-performing parts of a query and allows you to make informed optimizations.

Example:
```
EXPLAIN ANALYZE SELECT * FROM orders WHERE customer_id = 42;
```

### Joins and Join Strategies
Choosing the right join type (e.g., INNER JOIN, LEFT JOIN) and understanding join strategies (e.g., nested loop, hash join) can have a significant impact on query performance. PostgreSQL’s query planner often selects the best join strategy automatically, but you can influence it using query hints.

Example:
```
-- Using INNER JOIN for Efficient Matching
SELECT orders.* FROM orders
INNER JOIN customers ON orders.customer_id = customers.id;
```

## Table Partitioning
Table partitioning is a technique used to divide large tables into smaller, more manageable pieces called partitions. PostgreSQL offers native support for table partitioning, which can improve both query performance and maintenance tasks.

Example:
```sql
-- Creating a Partitioned Table by Range
CREATE TABLE logs (log_date DATE, message TEXT)
PARTITION BY RANGE (log_date);

-- Creating Partitions
CREATE TABLE logs_january PARTITION OF logs
FOR VALUES FROM ('2023-01-01') TO ('2023-02-01');
```

## Concurrency Control
Concurrency control is crucial for maintaining database performance in multi-user environments. PostgreSQL offers various isolation levels and locking mechanisms to manage concurrent access to data.

### MVCC (Multi-Version Concurrency Control)
PostgreSQL uses MVCC to handle concurrent transactions. Each transaction sees a snapshot of the database at a specific point in time, reducing the need for locks and improving concurrency.

Example:
```sql
-- Viewing the Current Transaction's Snapshot
SELECT * FROM products WHERE created_at < NOW();
```

## Advanced Configuration Tweaks
PostgreSQL provides a plethora of configuration options that can be fine-tuned to match your specific workload and hardware. Tweaking these settings can yield significant performance improvements.

Example:
```
# Increase Shared Memory
shared_buffers = 4GB

# Optimize Disk I/O
random_page_cost = 1.1

# Tune Autovacuum Settings
autovacuum_vacuum_scale_factor = 0.1
```

## Monitoring and Performance Tuning
Regularly monitoring your PostgreSQL database and tuning its performance based on real-world data is crucial. Tools like `pg_stat_statements` and `pgBadger` can help you gain insights into query performance and resource utilization.

Example:
```sql
-- Enabling pg_stat_statements Extension
CREATE EXTENSION pg_stat_statements;

-- Viewing the Most Time-consuming Queries
SELECT * FROM pg_stat_statements ORDER BY total_time DESC LIMIT 10;
```