# Different shell

# Identify default shell
- Open your terminal and enter `echo $0`. The output should be something like `-zsh` or `-bash` (or whatever shell you are using)
- You can also use `$SHELL`. To find the shell you have on the default environment you can check the value of the `SHELL` environment variable as `echo $SHELL`. You will get the output as `/bin/zsh`
- To find the current shell instance, look for the process (shell) having the PID of the current shell instance.
    - To find the PID of the current instance of shell: `echo "$$"`
    - Now to find the process having the PID: `ps -p <PID>`
    - Putting it together: `ps -p "$$"`

# Change default shell
- Use the `ch` command as follows: `chsh -s $(which zsh)`

# Todo:
- alias
- zshrc/bashrc


# Commands:
We can't `echo` commands by just calling them. That's why we include them within `()`. Try running `echo $uname` and it will yield nothing while `echo $(uname)` or just `uname` will return the result. Because within `()` in `$()` the command is executed and `$` will return the value

Example:
```sh
# Below displays current working directory
echo $(pwd)
pwd

# Below just displays variable names
$pwd / ${pwd} are different forms of using the variable ”pwd".
```

- `source`: source is a shell built-in command which is used to read and execute the content of a file(generally set of commands), passed as an argument in the current shell script.

    The command after taking the content of the specified files passes it to the TCL interpreter as a text script which then gets executed. If any arguments are supplied, they become the positional parameters when filename is executed. Otherwise, the positional parameters remain unchanged. The entries in `$PATH` are used to find the directory containing `FILENAME`, however if the file is not present in `$PATH` it will search the file in the current directory. The source command has no option and the argument is the file only.

    ```sh
    # Example
    source ~/.zshrc
    ```
- `typeset`: 

- `$uname`: return of the operating system being used
    `Darwin` for macOS
    `Linux` when run in Docker

