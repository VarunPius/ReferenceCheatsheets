# Python Variables
## `__name__`
Python sets the `__name__` variable to the module name, so the value of this variable will vary depending on the Python source file in which you use it.

For example, in a module named `test.py` that is located in the top-level directory of the application, the value of `__name__` is `test`. If the `test.py` module is located inside a Python package called `my_package`, then the value of `__name__` is `my_package.test`.

There are two special exceptions with regards to the value of `__name__`:

- Inside a `__init__.py` package constructor module, the value of `__name__` is the package name, without `__init__`. For example, in `my_package/__init__.py`, the value of `__name__` is just `my_package`.
- In the main module of the application (the file you run the Python interpreter on) the value of `__name__` has the special value of `__main__`. This is why we use `if __name__ == '__main__'`

# Data Types
## int()
How to convert a hex string to an int in Python? It may be `0xffff` or just `ffff`.
Without the 0x prefix, you need to specify the base explicitly, otherwise there's no way to tell:
```
x = int("deadbeef", 16)
```
With the `0x` prefix, Python can distinguish hex and decimal automatically.
```
>>> print(int("0xdeadbeef", 0))
3735928559
>>> print(int("10", 0))
10
```
(You must specify 0 as the base in order to invoke this prefix-guessing behavior; if you omit the second parameter int() will assume base-10.)

## List
Let's consider `a = [1, 2, 3, 4]`. To add element, use `append`. To remove last element, use `pop()`.

Here's some examples:
```
a = [1, 2, 3, 4]
a.append(5)     # a = [1, 2, 3, 4, 5]
a.pop()         # a = [1, 2, 3, 4]
a[2]            # 3
a[4]            # IndexError
a[-1]           # 4 (last element)
a[1:3]          # [2, 3] ; This is an example of slices
a[::2]          # Return list selecting every second entry => [1, 3]
# Format for slices is `a[start:end:step]`

# Delete element
del a[2]        # a is now [1, 2, 4]

# Remove first occurrence of a value
a.remove(2)     # a is now [1, 4]
a.remove(2)     # Raises a ValueError as 2 is not in the list

# other ways to delete:
num = list(filter(lambda x: x not in num_to_remove, num))   # using filter method
num = [i for i in num if i not in num_to_remove]            # list comprehensions


# Insert an element at a specific index
a.insert(1, 2)  # li is now [1, 2, 4] again

# Get the index of the first item found matching the argument
li.index(2)  # => 1
li.index(3)  # Raises a ValueError as 3 is not in the list

# You can add lists
# Note: values for li and for other_li are not modified.
li + other_li  # => [1, 2, 3, 4, 5, 6]

# Concatenate lists with "extend()"
li.extend(other_li)  # Now li is [1, 2, 3, 4, 5, 6]

# Check for existence in a list with "in"
1 in li  # => True

# Examine the length with "len()"
len(li)  # => 6
```

### Deep copy
Assignment is result is allocating same object to other variable. To do deep copy(i.e create a copy), use slices (`[:]`)
```
a = [1, 2, 3, 4]
b = a
print(b)
del b[2]
print(b)
print(a)
```

The output for above is:
```
[1, 2, 3, 4]
[1, 2, 4]
[1, 2, 4]
```

Deep copy example:
```
a = [1, 2, 3, 4]
b = a[:]
print(b)
del b[2]
print(b)
print(a)
```

The output for above is:
```
[1, 2, 3, 4]
[1, 2, 4]
[1, 2, 3, 4]
```


## Deque
### Slicing deque
Try `itertools.islice()`.
```
deque_slice = collections.deque(itertools.islice(my_deque, 10, 20))
```
Indexing into a deque requires following a linked list from the beginning each time, so the `islice()` approach, skipping items to get to the start of the slice, will give the best possible performance (better than coding it as an index operation for each element).

## Lambda Map Reduce Filter
asd

# Methods 
## staticmethods and classmethods

## sort() vs sorted()

## __variable__ vs __variable
https://stackoverflow.com/questions/1301346/what-is-the-meaning-of-single-and-double-underscore-before-an-object-name
https://www.datacamp.com/tutorial/role-underscore-python

## __new()__ vs __init()__

# extraToDo
https://stackoverflow.com/questions/19278861/issues-with-generating-a-defaultdict-with-a-deque
https://waymoot.org/home/python_string/
https://towardsdatascience.com/how-to-build-custom-context-managers-in-python-31727ffe96e1
https://towardsdatascience.com/an-in-depth-tutorial-to-python-decorators-that-you-can-actually-use-1e34d3d2d305
https://towardsdatascience.com/12-python-decorators-to-take-your-code-to-the-next-level-a910a1ab3e99
https://www.tutorialspoint.com/difference-between-and-and-amp-in-python
