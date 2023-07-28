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
