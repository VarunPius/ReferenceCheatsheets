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


# Methods 
## staticmethods and classmethods

## sort() vs sorted()