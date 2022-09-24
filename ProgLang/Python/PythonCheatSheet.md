# Start Virtual Environment
When developing software with Python, a basic approach is to install Python on your machine, install all your required libraries via the terminal, write all your code in a single `.py` file or notebook, and run your Python program in the terminal.

But in complex software development projects, like building a Python library, an API, or software development kit, often you will be working with multiple files, multiple packages, and dependencies. As a result, you will need to isolate your Python development environment for that particular project. This helps when project A uses library P with version x while project B uses same library P with version y. This is result in an error, or alternative is reinstalling the versions.

When you activate a virtual environment for your project, your project becomes its own self contained application, independent of the system installed Python and its modules. Your new virtual environment has its own pip to install libraries, its own libraries folder, where new libraries are added, and its own Python interpreter for the Python version you used to activate the environment.

With this new environment, your application becomes self-contained and you get some benefits such as:
- Dev environment is contained within your project, is isolated, and does not interfere with your system installed Python or other virtual environments
- Create a new virtual environment for multiple Python versions
- Download packages into your project without admin privileges
- Package application and share with other developers to replicate
- Create a list of dependencies and sub-dependencies in a file, for your project, which makes it easy for other developers to replicate and install all the dependencies used within your environment

Using virtual environments is recommended for software development projects that generally grow out of a single Python script, and Python provides multiple ways of creating and using a virtual environment.

Here, I am using `venv` (Virtualenv) but there are alternatives such as `pipenv`, which is a more high level approach.

## Install a Virtual Environment using Venv

Virtualenv is a tool to set up your Python environments. Since Python 3.3, a subset of it has been integrated into the standard library under the venv module. You can install venv to your host Python by running this command in your terminal:
```
pip install virtualenv
```

To use venv in your project, in your terminal, create a new project folder, cd to the project folder in your terminal, and run the following command:
```
python<version> -m venv <virtual-environment-name>
```

For example:
```
mkdir projectA
cd projectA
python3.8 -m venv venv
# or
python -m venv venv
```

When you check the new `projectA` folder, you will notice that a new folder called `venv` has been created. `venv` is the name of our virtual environment, but it can be named anything you want.

If we check the contents of `venv` for a bit, on a Mac you will see a `bin` folder. You will also see scripts that are typically used to control your virtual environment, such as activate and pip to install libraries, and the Python interpreter for the Python version you installed, and so on. 
> (This folder will be called `Scripts` on windows).

The `lib` folder will contain a list of libraries that you have installed. If you take a look at it, you will see a list of the libraries that come by default with the virtual environment.

## Activate the Virtual Environment
Now that you have created the virtual environment, you will need to activate it before you can use it in your project. On a Mac, to activate your virtual environment, run the code below:
```
source env/bin/activate
```

This will activate your virtual environment. Immediately, you will notice that your terminal path includes `venv`, signifying an activated virtual environment.

Note that to activate your virtual environment on Windows, you will need to run the following code below (See this link to fully understand the differences between platforms):
```
env/Scripts/activate.bat //In CMD
env/Scripts/Activate.ps1 //In Powershel
```

## Debugging
We have activated our virtual environment, now how do we confirm that our project is in fact isolated from our host Python? We can do a couple of things.

First we check the list of packages installed in our virtual environment by running the code below in the activated virtual environment. You will notice only two packages – `pip` and `setuptools`, which are the base packages that come default with a new virtual environment
```
pip list
```

Next you can run the same code above in a new terminal in which you haven't activated the virtual environment. You will notice a lot more libraries in your host Python that you may have installed in the past. These libraries are not part of your Python virtual environment until you install them.

## Install Libraries in a Virtual Environment
To install new libraries, you can easily just `pip install` the libraries. The virtual environment will make use of its own pip, so you don't need to use pip3.

After installing your required libraries, you can view all installed libraries by using `pip list`, or you can generate a text file listing all your project dependencies by running the code below:
```
pip freeze > requirements.txt
```

You can name this `requirements.txt` file whatever you want.

## Requirements File
To recreate your development environment for your friend will just need to follow the above steps to activate a new virtual environment. Instead of having to install each dependency one by one, they could just run the code below to install all your dependencies within their own copy of the project:
```
pip install -r requirements.txt
```

> Note that it is generally not advisable to share your `venv` folder, and it should be easily replicated in any new environment.

Typically your `venv` directory will be included in a `.gitignore` file (when using version control platforms like GitHub) to ensure that the environment file is not pushed to the project repository.

## Deactivate a Virtual Environment
To deactivate your virtual environment, simply run the following code in the terminal:
```
~ deactivate
```

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
