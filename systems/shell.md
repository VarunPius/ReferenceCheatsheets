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
