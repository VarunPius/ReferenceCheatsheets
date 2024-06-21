This document is for setting up and configuring zsh shell. ZSH will come with couple of files. 

ZSH consists of the following files and this describes the order in which they are sourced/executed:
- `/etc/zshenv` (no longer exists on macOS by default)
- `~/.zshenv`
- login mode:
  - `/etc/zprofile` (calling `path_helper`)
  - `~/.zprofile`
- interactive: `/etc/zshrc`, `~/.zshrc`
- login mode: `/etc/zlogin`, `~/.zlogin`

> Note: `shadowpirate` is my system username. Replace it with your username
> The value of `$HOME` is `/Users/shadowpirate`. This is same as `~`
> ~ is expanded ONLY if it is the first character of a word AND it is unquoted
> ```
> $ echo "~"
> ~
> $ echo foo~
> foo~
> $ echo ~
> /home/guest
> $ echo ~/foo
> /home/guest/foo
> ```

# ZSH Files:
Here a list of what each file should/shouldn't contain ideally (collected from StackOverflow):

## .zshenv
[Read every time]

This file is always sourced, so it should set environment variables which need to be updated frequently. `PATH` (or its associated counterpart `path`) is a good example because you probably don't want to restart your whole session to make it update. By setting it in that file, reopening a terminal emulator will start a new Zsh instance with the `PATH` value updated.

But be aware that this file is read even when Zsh is launched to run a single command (with the -c option), even by another tool like make. You should be very careful to not modify the default behavior of standard commands because it may break some tools (by setting aliases for example).

.zprofile
[Read at login]

I personally treat that file like .zshenv but for commands and variables which should be set once or which don't need to be updated frequently:

environment variables to configure tools (flags for compilation, data folder location, etc.)
configuration which execute commands (like SCONSFLAGS="--jobs=$(( $(nproc) - 1 ))") as it may take some time to execute.
If you modify this file, you can apply the configuration updates by running a login shell:

exec zsh --login
.zshrc
[Read when interactive]

I put here everything needed only for interactive usage:

prompt,
command completion,
command correction,
command suggestion,
command highlighting,
output coloring,
aliases,
key bindings,
commands history management,
other miscellaneous interactive tools (auto_cd, manydots-magic)...
.zlogin
[Read at login]

This file is like .zprofile, but is read after .zshrc. You can consider the shell to be fully set up at .zlogin execution time

So, I use it to launch external commands which do not modify shell behaviors (e.g. a login manager).

.zlogout
[Read at logout][Within login shell]

Here, you can clear your terminal or any other resource which was setup at login.

How I choose where to put a setting
if it is needed by a command run non-interactively: .zshenv
if it should be updated on each new shell: .zshenv
if it runs a command which may take some time to complete: .zprofile
if it is related to interactive usage: .zshrc
if it is a command to be run when the shell is fully setup: .zlogin
if it releases a resource acquired at login: .zlogout


# PATH variables
Everytime you `source` your shell files, be it `.zshrc` or `.zshenv`, it will execute all the `export` commands and keep appending to the `$PATH` variable. This will result in duplicates. How do we handle this?

Here are 3 ways to fix this:
- My way: simply hardcode value of `$PATH` and then add exports later
- Create scripts: create scipts to check if value exists in `$PATH` and if not then append else ignore
- Initial setup way: This is by far the best. A hybrid of the above 2. Like my way, sets values for your applications, but duplicates are found and deleted (but without script). Instead uses in-built functions

## My way
Set the value of `$PATH` in the beginning so that everytime the file is `source`d it will start from the set value and then the `export`ed values get appended.
Example:
```sh
# Avoid this:
export PATH="/usr/local/opt/openjdk/bin:$PATH"
# Everytime you run source, the jdk path keeps getting appended to the $PATH

# Try this:
# Set start value:
export PATH="/System/Cryptexes/App/usr/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"

# Then do the exports
# JDK Export:
export PATH="/usr/local/opt/openjdk/bin:$PATH"
# Cargo/Rust Export:
export PATH="/Users/shadowpirate/.cargo/bin:$PATH"
# PyENV Export and Settings:
export PYENV_ROOT="$HOME/.pyenv"
command -v pyenv >/dev/null || export PATH="$PYENV_ROOT/bin:$PATH"
eval "$(pyenv init -)"
# Poetry export:
export PATH="$HOME/.local/bin:$PATH"
```

I added the above script in `~/.zshrc`, but the official Linux manuals suggests adding it in `~/.zshenv`. Read the attached file [PATH_Location](PATH_Location.md) about how `path_helper` reorders macOS `PATH` order. In short, irrespective in what order you add locations to your `$PATH` variable, `path_helper` in Mac will reorder to place system directories first. This might break somethings if order is paramount to you. How to fix this? Simply add `PATH` after `path_helper` has been called viz. after `~/.zprofile`.


## Create scripts
These scripts will check if the value exists in `PATH` and if yes then ignore, else append:
The script looks like this:
```sh
#!/bin/sh
# rustup shell setup
# affix colons on either side of $PATH to simplify matching
case ":${PATH}:" in
    *:"$HOME/.cargo/bin":*)
        ;;
    *)
        # Prepending path in case a system-installed rustc needs to be overridden
        export PATH="$HOME/.cargo/bin:$PATH"
        ;;
esac
```

What happened above? Try to understand how the following finding the substring in a string works:
```sh
#!/bin/bash

STR='GNU/Linux is an operating system'
SUB='Linux'

# 1: Using wildcards:
if [[ "$STR" == *"$SUB"* ]]; then
  echo "It's there."
fi

# 2: Using case:
case $STR in
  *"$SUB"*)
    echo -n "It's there."
    ;;
esac

# 3: Using REGEX:
if [[ "$STR" =~ .*"$SUB".* ]]; then
  echo "It's there."
fi
```

Rust official installation uses case 2 to identify `cargo` in `PATH`.

Once the script is created, place it under `~/.config`. For Rust, however, it's kept under `$HOME/.cargo` as `env`. To each his own.

Next, in your `.zshenv` or `.zshrc` file, add the following:
```sh
# Rust example
. "$HOME/.cargo/env"

# Script file location:
. $HOME/.config/updatescript.sh
# or
. $HOME/.config/env
```

So now, when we `source .zshenv` or `source .zshrc` file, it will read the line and ergo execute the script from the config location.

## Initial setup way:
Edit your `.zshenv` file as follows:
```sh
# ########################
# Environment variables  #
# ########################
#
export EDITOR=nvim
export PAGER=less
export ZDOTDIR=$HOME/.config/zsh
export XDG_CONFIG_HOME=$HOME/.config
export KERNEL_NAME=$( uname | tr '[:upper:]' '[:lower:]' )

# remove duplicate entries from $PATH
# zsh uses $path array along with $PATH 
typeset -U PATH path

# user compiled python as default python
export PATH=$HOME/python/bin:$PATH
export PYTHONPATH=$HOME/python/

# user installed node as default node
export PATH="$HOME/node/node-v16.0.0-${KERNEL_NAME}-x64"/bin:$PATH
export NODE_MIRROR=https://mirrors.ustc.edu.cn/node/
	
case $KERNEL_NAME in
    'linux')
        source "$HOME/.cargo/env"
        ;;
    'darwin')
        PATH:/opt/local/bin:/opt/local/sbin:$PATH
        ;;
    *) ;;
esac
```

Explaining the above script:
- `$uname` return `Darwin` for macOS; `Linux` from withing Docker
- `tr '[:upper:]' '[:lower:]'`: Linux command for translate. Here it converts from uppercase to lowercase
- `typeset -U`: return unique values for `PATH` 
    In the beginning, default paths are defined in the `/etc/paths` file or the `/etc/paths.d` directory on macOS. Now, `PATH` is a string and `path` is a array both pointing or defining the same thing, i.e directories. ZSH ties the `PATH` variable to a `path` array. The `$path` array variable is tied to the `$PATH` scalar (string) variable. Any modification on one is reflected in the other.This allows you to manipulate `PATH` by simply modifying the `path` array.
    The format of `$PATH` such as `path1:path2:path3` is actually string format.
    Originally the values come in array format and then the `typeset -U` command eliminates all duplicates.
    Here's an example:
    ```sh
    #!/bin/zsh

    x=("te" "er" "esd" "te")    # Array declaration
    typeset -U x                # converting to unique values
    echo $x                     # te er esd

    arr=(1 2 3 4 3 2 1)
    echo ${(u)arr[@]}           # `u` indicates print only unique
    echo ${(u)xarr}             # if KSH_ARRAYS option is not set

    x=$(printf '%s:' ${x[@]})   # Converting array to string
    export x="te":$x            # appending new values
    echo $x
    ```
    As per the ArchLinux man pages, the line `typeset -U PATH path`, where the `-U` stands for unique, instructs the shell to discard duplicates from both `$PATH` and `$path`

