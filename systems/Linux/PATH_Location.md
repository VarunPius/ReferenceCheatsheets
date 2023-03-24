# About
This file is written in part due to the issue in new Silicon Macs where irrespective of where your add your `PATH`, it always gets appended in the end after system variables, which has a way of breaking some things sometimes. Here in lies the justification to use `zshrc` instead of `zshenv`.

# Problem
- [macOS, Zsh, and more](#macos--zsh--and-more)
  * [Zsh initializations](#zsh-initializations)
  * [Zsh way of $PATH](#zsh-way-of--path)
  * [the special macOS](#the-special-macos)
  * [`/usr/libexec/path_helper`](#--usr-libexec-path-helper-)
    + [What is it?](#what-is-it-)
    + [What does it do?](#what-does-it-do-)
    + [What does it break?](#what-does-it-break-)
  * [Situation explained](#situation-explained)
    + [The  `.*shrc` file](#the----shrc--file)
    + [Order is key and everything!](#order-is-key-and-everything-)
    + [Bash](#bash)
    + [Zsh](#zsh)
  * [Solutions](#solutions)
    + [Choosing the right init file](#choosing-the-right-init-file)
    + [Using replacements](#using-replacements)
    + [Switch back to Bash](#switch-back-to-bash)

# macOS, Zsh, and more 
This gist is based on a twitter
[thread](https://twitter.com/giftboiling/status/1386359878797565955?s=20). 
I just want to add a few more details on this topic.

## Zsh initializations
I'm not going to repeat what has alreay  been well documented:
When zsh starts up, it looks for a few [startup
files](http://zsh.sourceforge.net/Guide/zshguide02.html#l). Among them,
`~/.zshenv` and `~/.zshrc` are the most relevant to a normal user like me. They are also the focus of this gist.

Both the [Zsh User Guide](http://zsh.sourceforge.net/Guide/zshguide02.html#l23)
and [Arch Wiki](https://wiki.archlinux.org/index.php/Zsh#Configuring_$PATH)
suggest that I put environment variables such as `$PATH` in my `~/.zshenv`. I
followed the advice and my `~/.zshenv` looks like this:

```shell    
# ########################
# Environment variables  #
# ########################
#
export EDITOR=nvim
export PAGER=less
export ZDOTDIR=$HOME/.config/zsh
export XDG_CONFIG_HOME=$HOME/.config
export KERNEL_NAME=$( uname | tr '[:upper:]' '[:lower:]' )

# remove duplicat entries from $PATH
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
As you can see, I prefer to use my own Python and Nodejs under my `$HOME` while
leaving the system ones untouched. Kind of clean in my eyes. And it'd worked
pretty well on all my Linux/\*BSD machines. 

The `$KERNEL_NAME` and `case` statement is there for zsh to detect which OS I'm on.
If I'm on macOS, prepare all that is needed for MacPorts and macOS Nodejs. 

## Zsh way of $PATH
It can be confusing. Have you ever met:
1. [PATH variable in .zshenv or .zshrc](https://stackoverflow.com/a/34244862)
2. [Weird behaviour with zsh PATH](https://stackoverflow.com/questions/29820315/weird-behaviour-with-zsh-path)
3. [zsh can neither find nor execute custom user scripts in ~/bin](https://stackoverflow.com/questions/62251500/zsh-can-neither-find-nor-execute-custom-user-scripts-in-bin-although-they-are)

The only difficulty that had baffled me was zsh's way of setting $PATH. It is
NOT that you can't do `export PATH=...` stuff, but that zsh provides an array, `$path` which is
tied to `$PATH`, meaning that you can either change `$PATH` using the `export`
convention (changing a scalar string) or change `$path` (lowercase, an array) and makes it 
easier to append, prepend, and even insert new
paths to the exisiting one.

Over time, `$PATH` can become quite messy, including a lot of duplicate entries.
Thus, `$path` comes in handy and there are already many mentioning/talking about
this. Just to list a few:
1. [Arch Wiki](https://wiki.archlinux.org/index.php/Zsh#Configuring_$PATH):
>The line `typeset -U PATH path`, where the `-U` stands for unique, instructs
>the shell to discard duplicates from both `$PATH` and `$path`
2. [Zsh User Guide](http://zsh.sourceforge.net/Guide/zshguide02.html#l24):
>The incantation `typeset -U path`, where the `-U` stands for unique, tells the
>shell that it should not add anything to `$path` if it's there already.
3. [StackOverFlow](https://stackoverflow.com/a/18077919):
>ZSH allows you to use special mapping of environment variables ... For me
>that's a very neat feature which can be propagated to other variables.
4. [StaclExchange](https://unix.stackexchange.com/a/532155):
>That's useful in that it makes it easier to add remove components or loop over them.
5. [StackExchange](https://superuser.com/a/1447959):
>...benefit of using an array ...

## The special macOS
The above `$path` and `$PATH` kept working well until I tried to migrate them to my M1 running macOS. If I run `echo $PATH` or `echo $path`, I got this:

```
/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Applications/VMware Fusion.app/Contents/Public:/Library/TeX/texbin:/Library/Frameworks/Mono.framework/Versions/Current/Commands:/opt/local/bin:/opt/local/sbin:/Users/leon/node/node-v16.0.0-darwin-x64/bin:/Users/leon/python/bin
```
It is weird that even I **prepend** some paths to `$PATH`, they end up appearing at the last! On the other hand, the paths for system utilities come first! How come? I searched a lot and finally nailed down the root cause: `/usr/libexec/path_helper`.

## `/usr/libexec/path_helper`
### What is it?
It is a binary file. No way to see the source code. But as is the case with many unix utilities, it has a [man page](https://www.manpagez.com/man/8/path_helper/):
```
path_helper(8)            BSD System Manager's Manual           path_helper(8)

NAME
     path_helper -- helper for constructing PATH environment variable

SYNOPSIS
     path_helper [-c | -s]

DESCRIPTION
     The path_helper utility reads the contents of the files in the direc-
     tories /etc/paths.d and /etc/manpaths.d and appends their contents to
     the PATH and MANPATH environment variables respectively.  (The MANPATH
     environment variable will not be modified unless it is already set in
     the environment.)

     Files in these directories should contain one path element per line.

     Prior to reading these directories, default PATH and MANPATH values
     are obtained from the files /etc/paths and /etc/manpaths respectively.

     Options:

     -c      Generate C-shell commands on stdout.  This is the default if
             SHELL ends with "csh".

     -s      Generate Bourne shell commands on stdout.  This is the default
             if SHELL does not end with "csh".

NOTE
     The path_helper utility should not be invoked directly.  It is
     intended only for use by the shell profile.

Mac OS X                        March 15, 2007                        Mac OS X
(END)
```
This has been mentioned in a few places on the Internet too:
1. [Setting system path for command line on Mac OSX](https://www.chrissearle.org/2014/01/28/setting-system-path-for-command-line-on-mac-osx/)
2. [Where is the default terminal $PATH located on Mac?](https://izziswift.com/where-is-the-default-terminal-path-located-on-mac/)
3. [Where is the default terminal $PATH located on Mac?](https://stackoverflow.com/questions/9832770/where-is-the-default-terminal-path-located-on-mac) (Stack OverFlow)

### What does it do?
From the man page and info above, it is pretty clear what this tool does:
1. it looks for the file `/etc/paths` and files under `/etc/paths.d/`
2. it combines all the paths in these files
3. it returns the combined paths (as a string) and assigns it to `$PATH`

It is easy to confirm this. Simply run: `$ /usr/libexec/path_helper`

And it returns something like:

```
PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Applications/VMware Fusion.app/Contents/Public:/Library/TeX/texbin:/Library/Frameworks/Mono.framework/Versions/Current/Commands:/opt/local/bin:/opt/local/sbin:/Users/leon/node/node-v16.0.0-darwin-x64/bin:/Users/leon/python/bin";
export PATH 
``` 
On a fresh macOS, it can be as simple as
`PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin"; export $PATH`.  This article,
[Where PATHs come
from](https://scriptingosx.com/2017/05/where-paths-come-from/), explains pretty
much everything about `$PATH` on macOS.

Here is one more: [Mastering the path_helper utility of MacOSX](https://www.softec.lu/site/DevelopersCorner/MasteringThePathHelper)

### What does it break?
The only problem, in my opinion, is that `path_helper` **re-orders** the
`$PATH`! *Where PATHs come from* mentioned this:
>We have seen `path_helper` is extremely useful. There is one caveat, however.
>`path_helper` may reorder your `PATH`. Imagine you are pre-pending `~/bin` to
>your `PATH` because you want to override some standard tools with your own.
>(Dangerous, but let’s assume you know what you are doing.) Then some process
>launches a subshell which can call `path_helper` again. `path_helper` will
>‘find’ your additions to the defined `PATH`, but it will append to the list of
>default paths from `/etc/paths` and `/etc/paths.d`, changing your order and
>thus which tools will be used.

Note it will **append** any user-level path to the default one, which breaks
things. [This
guy](https://github.com/sorin-ionescu/prezto/issues/381#issuecomment-12800590)
was so angry that he thought `path_helper` is **"broken"**:
>@mislav, I understand how it works. It's broken.
>
>I also understand that having it enabled causes problems. You forget the path
>set in ~/.MacOSX/environment.plist, which is needed for GUI apps, and
>Terminal.app, being a GUI app, will get that environment before it launches a
>shell subprocesses.
>
>`path_helper` reorders the PATH entries set in environment.plist, which causes
>GUI programs to fail when they execute their scripts via a shell subprocess. A
>good example is any editor that depends on Exuberant Ctags. `path_helper`
>reorders the PATH entries and /usr/bin/ctags gets executed instead of Exuberant
>Ctags that is installed elsewhere. The editor complains that the wrong ctags is
>installed.
>
>If I don't set the PATH in environment.plist for GUI apps, then they complain
>that they can't find programs.
>
>Setting PATH on Mac OS X is a mess, and `path_helper` doesn't help.

**Indeed, I recommend reading the whole thread/discussion, as it provides a great deal of useful information.** I'll quote one more here to explain why I don't recommend to modify `/etc/paths` stuff or diable `/usr/libexec/path_helper`. 

[@mislav](https://github.com/mislav)'s this comment [argues](https://github.com/sorin-ionescu/prezto/issues/381#issuecomment-12792587) that:
1. `path_helper` does what it is supposed to: generate `$PATH` for macOS
2. forget having disabled it might cause further trouble in the future.

**NOTE**: `path_helper` also does not help with properly setting up `$MANPATH`:
1. [OS X: read /etc/manpaths and /etc/manpaths.d #1092](https://github.com/fish-shell/fish-shell/issues/1092#issuecomment-189576438)
2. [Mac OS X man command ignores $MANPATH (which sucks for HomeBrew installed commands)](https://www.skepticism.us/posts/2015/10/mac-os-x-man-command-ignores-manpath-which-sucks-for-homebrew-installed-commands/)
3. [path_helper will not set MANPATH](https://discussions.apple.com/thread/2187861)

## Situation explained
Let me ask one more question: why rarely did I find many people complaining about `path_helper` breaking their Homebrew on macOS?

### The  `.*shrc` file
The answer is simple: many, really many, macOS CLI tools ask users to modify their `.bashrc` or `.zshrc`, rather than `.bash_profile` or `.zshenv`. This is the very key to the solutions to my issue. Read on.

### Order is key and everything!
The order in which a shell's startup files get sourced/read **matters** most! You'd better believe this.

[@mislav](https://github.com/mislav) also has this detailed [write-up](https://github.com/sstephenson/rbenv/wiki/Unix-shell-initialization). 

### Bash
On macOS, for [bash](https://github.com/rbenv/rbenv/wiki/Unix-shell-initialization#bash), these files are source in the following order:

1. **login** mode:
    1. `/etc/profile` (calling `path_helper`)
    2. `~/.bash_profile`,` ~/.bash_login`, `~/.profile` (only first one that exists)
2. interactive **non-login**:
    1. `/etc/bash.bashrc` (some Linux; not on Mac OS X)
    2. `~/.bashrc`
3. **non-interactive**:
    1. source file in `$BASH_ENV`

Since macOS always opens a login shell when you start a new terminal window, the `/etc/profile` always gets sourced. This file is very simple:

```
# System-wide .profile for sh(1)

if [ -x /usr/libexec/path_helper ]; then
	eval `/usr/libexec/path_helper -s`
fi

if [ "${BASH-no}" != "no" ]; then
	[ -r /etc/bashrc ] && . /etc/bashrc
fi
```

After this file, `$PATH` is set using the contents from `/etc/paths` and `files under `/etc/paths.d`. 

Then if a user modifies their `$PATH` in any of `~/.bash_profile`, `~/.bashrc`, or `~/.profile`. `$PATH` acts as expected. A user may append or prepend to it some other paths:
```shell
# prepend a path
export PATH=/some/path:$PATH

# append a path
export PATH=$PATH:/some/path
```

### Zsh
For [zsh](https://github.com/rbenv/rbenv/wiki/Unix-shell-initialization#zsh), the order is like this:
1. `/etc/zshenv` (no longer exists on macOS by default)
2. `~/.zshenv`
3. **login** mode:
    1. `/etc/zprofile` (calling `path_helper`)
    2. `~/.zprofile`
4. **interactive**:
        `/etc/zshrc`
        `~/.zshrc`
5. **login** mode:
       `/etc/zlogin`
        `~/.zlogin`

If you, like me, set `$PATH` in `~/.zshenv`, it gets sourced first and `$PATH` then becomes something like:

```
# user may append and prepend their paths to the default system one
/some/path:/usr/local/bin:/usr/bin:/bin/....:/some/other/path
```

Then, on macOS, since it is always login shell in a new terminal window, `/etc/zprofile` gets sourced and calls `path_helper`. At this time, `path_helper` sees your `$PATH` already contains the default from `/etc/paths` and `/etc/paths.d`, it will **NOT** add anything new to it.

However, it will **RE-ORDER** the `$PATH` to make it like:
```
/usr/local/bin:/usr/bin:....:/user/appended/or/prepended/paths
```
As I have shown in the beginning ... and this, well, breaks things.

Here is an article on **login shells**: [What is Login Shell in Linux?](https://linuxhandbook.com/login-shell/), just in case this confuses you.

## Solutions
The key lies in the idea that we should start appending or prepending the `$PATH` after `path_helper` has been called. 
### Choosing the right init file
Using bash, that would be somewhere in `~/.bashrc` or `~/.bash_profile`.

Using zsh, on macOS, avoid `~/.zshenv` and choose `~/.zshrc` or `~/.zprofile` instead. In fact, better to replace `~/.zshenv` with `~/.zprofile` so that both Linux and macOS will use the same files with the very same config.

**NOTE**:

You might have symbolic links to `~/.zshrc` or so, just as I did. After you make proper changes, remember also to fix the symbolic links so that they keep pointing to the right files. In my case, I decided to switch to `~/.zshrc`, but I forgot changing the previous `~/.zshenv@` (a symlink), so my `$PATH` didn't look right!

What if I insist on sticking to *Zsh User Guide*? See this message from Zsh mailing list: [Re: ~/.zshenv or ~/.zprofile](https://www.zsh.org/mla/users/2003/msg00600.html)

The following two articles might be as helpful:
1. [Moving to zsh, part 2: Configuration Files](https://scriptingosx.com/2019/06/moving-to-zsh-part-2-configuration-files)
2. [Setting the PATH in Scripts](https://scriptingosx.com/2018/02/setting-the-path-in-scripts)

### Using replacements
1. [yb66/path_helper ](https://github.com/yb66/path_helper)
2. [otaviof/path-helper ](https://github.com/otaviof/path-helper)
3. [mgprot/path_helper](https://github.com/mgprot/path_helper)

### Switching back to Bash
If zsh doesn't mean anything special to you (or your workflow), especailly when you write shell scripts in a Bash way, why not just Bash then?
