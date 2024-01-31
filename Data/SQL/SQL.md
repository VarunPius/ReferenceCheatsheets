# EXISTS and NOT EXISTS

# COUNT(*) vs COUNT(Col)
Between `COUNT(*)` vs `COUNT(1)`, nobody is more performant than the other; it’s a draw; they’re exactly the same.
However, I’d recommend using COUNT(*), as it’s much more commonly seen.
It’s also less confusing, naturally leading other SQL users to understand that the function will count all the numbers in the table, including the NULL values.

However, `COUNT(column name)` will only count rows where the given column is **NOT NULL**.

> More reading: https://learnsql.com/blog/difference-between-count-distinct/


# Window functions
https://www.sqltutorial.org/sql-window-functions/

# Amazon
https://www.sqlshack.com/internals-of-physical-join-operators-nested-loops-join-hash-match-join-merge-join-in-sql-server/
https://www.datacamp.com/tutorial/introduction-indexing-sql

# String_agg
https://www.postgresqltutorial.com/postgresql-aggregate-functions/postgresql-string_agg-function/

# UNNEST

# first day or last day of the month:
```sql
-- last day
SELECT
    (DATE_TRUNC('month', '2017-01-05'::date) + INTERVAL '1 month' - INTERVAL '1 day')::date
AS end_of_month;
```

# Partitioning vs clustering
https://nidhig631.medium.com/partitioning-vs-clustering-126a8c3cb1ee