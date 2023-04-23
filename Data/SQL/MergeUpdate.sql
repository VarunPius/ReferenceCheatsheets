/*
What happens when multiple rows have same value during joins and we have to do merge update.
- When single source row matches multiple target row, all rows are updated.
- When multiple source row matches multiple target row, it results in an error.
- When multiple source row matches single target row, it results in an error.
*/

CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE IF NOT EXISTS test.EmployeeSalary (
     id INT
   , empid INT
   , Salary INT
);

TRUNCATE TABLE test.EmployeeSalary;

INSERT INTO test.EmployeeSalary (id, empid, Salary) VALUES (1, 1, 400);
INSERT INTO test.EmployeeSalary (id, empid, Salary) VALUES (2, 2, 500);
INSERT INTO test.EmployeeSalary (id, empid, Salary) VALUES (3, 3, 600);
INSERT INTO test.EmployeeSalary (id, empid, Salary) VALUES (1, 1, 400);

SELECT * FROM test.EmployeeSalary;


CREATE TABLE IF NOT EXISTS test.EmployeeSalary_stg (
     id INT
   , empid INT
   , Salary INT
);

TRUNCATE TABLE test.EmployeeSalary_stg;

INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (4, 4, 700);
INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (5, 5, 800);
INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (6, 6, 900);
--INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (7, 1, 1000);

SELECT * FROM test.EmployeeSalary_stg;

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------

-- Merge:

MERGE INTO 
   test.EmployeeSalary t
USING
   test.EmployeeSalary_stg s
      ON s.empid = t.empid
WHEN MATCHED THEN
   UPDATE SET
      id = s.id,
      salary = s.salary
WHEN NOT MATCHED THEN
   INSERT (id, empid, Salary)
   VALUES(s.id, s.empid, s.Salary)
;

/*
    id|empid|salary|
    --+-----+------+
    1 |    1|   400|
    2 |    2|   500|
    3 |    3|   600|
    1 |    1|   400|
    4 |    4|   700|
    5 |    5|   800|
    6 |    6|   900|
*/


----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- Case 1: Duplicate records already in target table; inserting same value from source table
-- empid 1 already exists in tgt tbl duplicated. 
INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (7, 1, 1000);

-- Merge Update will simply update both the duplicated records 
/*
    id|empid|salary|
    --+-----+------+
    2 |    2|   500|
    3 |    3|   600|
    4 |    4|   700|
    5 |    5|   800|
    6 |    6|   900|
    7 |    1|  1000|
    7 |    1|  1000|
*/

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- Case 2: Duplicate records in target table; duplicated records in source table
-- empid 1 already exists in tgt tbl duplicated. 
INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (7, 1, 900);
INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (8, 1, 800);

-- Merge Update will simply result in an error.
/*
SQL Error [21000]: ERROR: MERGE command cannot affect row a second time
  Hint: Ensure that not more than one source row matches any one target row.
*/

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
-- Case 3: Single record in target table; duplicated records in source table
-- empid 2 has only one record in tgt table
INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (7, 2, 900);
INSERT INTO test.EmployeeSalary_stg (id, empid, Salary) VALUES (8, 2, 800);

-- Merge Update will simply result in an error.
/*
SQL Error [21000]: ERROR: MERGE command cannot affect row a second time
  Hint: Ensure that not more than one source row matches any one target row.
*/
