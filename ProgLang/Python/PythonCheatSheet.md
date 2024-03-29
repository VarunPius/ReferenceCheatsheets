# Process Files:
Once a file is processed when you open it and read it with `with open() as var` and an operation is performed, the processor moves on. So you will get an error when you try to process the `var` twice within a block. Copy the values of `var` if you want to process it multiple times. Let's take an example:
```
conffile = os.path.join(confdir, 'conf.yaml')
with open(conffile, "r") as ymlconf:
    try:
        print(yaml.safe_load(ymlconf))  # @1
        conf = yaml.safe_load(ymlconf)  # @2
    except yaml.YAMLError as exc:
        print(exc)
```
Here, in the above example, when it processed `@1`, the print was executed. However, at `@2`, `conf` will result in `None` as the file was already loaded with `safe_load` a line before. To avoid errors like this, do the following:
```
with open(conffile, "r") as ymlconf:
    try:
        conf = yaml.safe_load(ymlconf)
        print(conf)  
    except yaml.YAMLError as exc:
        print(exc)
```
Here, `ymlconf` is loaded and copied into the `var` variable and you can process it accordingly.

## Process general files:
```
import os
if not os.path.exists('my_folder'):
    os.makedirs('my_folder')
```

## Process CSV:
```
import csv
# Define data
data = [
    (1, "A towel,", 1.0),
    (42, " it says, ", 2.0),
    (1337, "is about the most ", -1),
    (0, "massively useful thing ", 123),
    (-2, "an interstellar hitchhiker can have.", 3),
]

# Write CSV file
with open("test.csv", "wt") as fp:
    writer = csv.writer(fp, delimiter=",")
    # writer.writerow(["your", "header", "foo"])  # write header
    writer.writerows(data)

# Read CSV file
with open("test.csv") as fp:
    reader = csv.reader(fp, delimiter=",", quotechar='"')
    # next(reader, None)  # skip the headers
    data_read = [row for row in reader]
print(data_read)

# Another Approach
with open('test.csv', 'r') as file:
    reader = csv.reader(file)
    for row in reader:
        print(row)
```

## Process JSON:
```
import json

# Write data
with open('data.json', 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=4)
  
# Opening JSON file
f = open('data.json')
  
# returns JSON object as 
# a dictionary
data = json.load(f)
f.close()
```

## Process YAML:
Assume the following YAML file:
```
Redis:
    server: redis-test.us-sample.ec2.cloud.redislabs.com:19079
    password: delta1234
MySQL:
    server: mysql-test.us-sample.ec2.cloud.sqllabs.com:1001
    password: delta12345
```

This is how we process it:
```
import yaml
conffile = os.path.join(confdir, 'conf.yaml')
with open(conffile, "r") as file:
    try:
        conf = yaml.safe_load(file)
    except yaml.YAMLError as exc:
        print(exc)

for db in conf:
    print(db)       # will print `Redis` and `MySQL`

print(db['Redis']['server'])    # will print Redis server
```

We have used the `safe_load` method instead of `load` as it should always be preferred to avoid introducing the possibility for arbitrary code execution.


# Best Tips and Tricks
Best of Python's lesser known functions and tricks:
- **Ternary operator**: The ternary operator is a shorthand for an if-else statement. The syntax is `value_if_true if condition else value_if_false`
  ```
  max = a if a > b else b  ##value_if_true if condition else value_if_false
  ```

- **Enumerate**: The `enumerate()` function adds a counter to an iterable.
  ```
  fruits = ['apple', 'banana', 'mango'] 
  for index, fruit in enumerate(fruits): 
    print(index, fruit)
  ```

- **Zip**: aggregates elements from each of the iterables and returns an iterator of tuples
  ```
  list1 = [1, 2, 3] 
  list2 = ['a', 'b', 'c', 'd'] 
  for x, y in zip(list1, list2):
      print(x, y)
  #1 a
  #2 b
  #3 c
  ```

- **List comprehensions**: concise way to create a list from an existing list or any iterable
  ```
  squared_numbers = [x**2 for x in range(1, 6)]
  ```

- **Lambda functions**: anonymous functions that are defined using the lambda keyword
  ```
  add = lambda x, y: x + y 
  result = add(3, 4)
  ```

- **Any and all functions**: The `any()` and `all()` functions return `True` or `False` based on the truthiness of the elements in an iterable. The `any()` function returns `True` if any element in the iterable is true, and `all()` function returns `True` if all elements in the iterable are true.
  ```
  numbers = [1, 2, 3, 0, 4] 
  result = any(numbers) #True 
  result = all(numbers) # False. 0 is making it false 
  ```

- **Itertools**: The `itertools` module provides a set of functions to work with iterators and is not widely known. Some of the functions in this module are `chain`, `product`, and `permutations`.
  ```
  import itertools 
  numbers = [1, 2, 3] 
  result = list(itertools.permutations(numbers)) 
  #output all the permutations 
  #[(1, 2, 3), (1, 3, 2), (2, 1, 3), (2, 3, 1), (3, 1, 2), (3, 2, 1)]
  ```

- **Generators**: type of iterable that generate values on-the-fly, instead of storing them in memory. They are defined using the `yield` keyword and can be used to create custom iterators.
  ```
  ### Generators created using yield keyword 
  def fibonacci_series(n):
      a, b = 0, 1
      for i in range(n):
          yield a
          a, b = b, a + b
  
  # Driver code to check above generator function 
  for number in fibonacci_series(10):
      print(number)
  
  #0
  #1
  #1
  #2
  #3
  #5
  #8
  #13
  #21
  #34
  ```

- **Decorators**: way to modify the behavior of a function or a class. They are defined using the `@` symbol and can be used to add functionality to a function, such as logging, timing, or authentication.
  ```
  def log_function(func):
      def wrapper(*args, **kwargs):
          print(f'Running {func.__name__}')
          result = func(*args, **kwargs)
          print(f'{func.__name__} returned {result}')
          return result
      return wrapper
  
  @log_function
  def add(x, y):
      return x + y
  
  
  print(add(5,7))
  
  #Running add
  #add returned 12
  #12
  ```

- **Multiple Function Arguments**: In Python, you can use the `*` and `**` operator to handle multiple function arguments.
  The `*` operator is used to pass a list of arguments as separate positional arguments, and the `**` operator is used to pass a dictionary of keyword arguments.
  ```
  def print_arguments(*args, **kwargs):
      print(args)
      print(kwargs)
  
  print_arguments(1, 2, 3, name='John', age=30)
  
  #(1, 2, 3)
  #{'name': 'John', 'age': 30}
  ```

- **Dynamic Importing**: You can import a module dynamically using the `importlib` module.
  This can be useful when you want to import a module based on user input or configuration.
  ```
  import importlib
  
  module_name = 'math'
  module = importlib.import_module(module_name)
  result = module.sqrt(9)
  ```

- **Dictionary Comprehensions**: Dictionary comprehensions are a concise way to create a dictionary from an existing dictionary or any iterable. It’s a one-liner that can replace a for loop, making your code more efficient and readable.
  ```
  squared_numbers = {x: x**2 for x in range(1, 6)}
  print(squared_numbers)
  
  #{1: 1, 2: 4, 3: 9, 4: 16, 5: 25}
  ```

- **Callable Objects**: anything that can be called a function is called a callable object.
  This includes functions, methods, classes, and even objects that define the `__call__` method.
  ```
  class Adder:
      def __call__(self, x, y):
          return x + y
  
  adder = Adder()
  result = adder(3, 4)
  
  print(result)
  #7
  ```

- **You can separate big numbers/characters with the underscore**: Big numeric numbers are hard to interpret, so python has a great capability to put underscore to make the numbers more readable.
  ```
  num_test = 100_345_405 # this is the number
  print(num_test)
  ## 100345405
  ```

- **Merge 2 dictionaries quickly**: We can quickly merge 2 dictionaries in python using the following piece of code.
  ```
  dictionary_one = {"a": 1, "b": 2}
  dictionary_two = {"c": 3, "d": 4}
  merged = {**dictionary_one, **dictionary_two}
  
  print(merged)  
  # {'a': 1, 'b': 2, 'c': 3, 'd': 4}
  ```

- **Lists, sets, and dictionaries are mutable**: Mutable means that we can change or update the object(list, set or dictionary) without changing the pointer of the object in the memory.
  In the below example, we are updating the cities list by appending a new city.
  We can see that the ID (object pointer) remains the same. The same is true for the sets and dictionary
  ```
  cities = ["Munich", "Zurich", "London"]
  print(id(cities)) # 2797174365184
  cities.append("Berlin")
  print(id(cities)) # 2797174365184
  
  ####Sets 
  my_set = {1, 2, 3}
  print(id(my_set))  # 2797172976992
  my_set.add(4)
  print(id(my_set))  # 2797172976992
  
  ###Dictionary
  thisdict = {
    "brand": "Ford",
    "model": "Mustang",
    "year": 1964
  }
  print(id(thisdict))  #2797174128256
  thisdict["engine"] = "2500cc"
  print(id(thisdict))  #2797174128256
  ```

- **Map, reduce, filter**:
  The `map()` function takes input and applies the function to all inputs: `map(function_to_apply, list_of_inputs)`
  ```
  items = [1, 2, 3, 4, 5]
  squared = list(map(lambda x: x**2, items))
  ```

  The `filter()` function is used to generate an output list of values that return true when the function is called. It has the following syntax: `filter (function, iterables)`
  ```
  def func(x):
      if x>=3:
          return x
  y = filter(func, (1,2,3,4))  

  # Using Lambda
  y = filter(lambda x: (x>=3), (1,2,3,4))
  ```

  The `reduce()` function applies a provided function to ‘iterables’ and returns a single value, as the name implies.
  It has the following syntax: `reduce(function, iterables)`
  ```
  from functools import reduce
  reduce(lambda a,b: a+b,[23,21,45,98])
  ```