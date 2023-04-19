# Install Neovim
For Mac, you can use **HomeBrew**:
```
brew install neovim
```

Next, add `alias` in `.zshrc`:
```
alias vim='nvim'
```


# NVCHAD
## Delete previous configurations:
- If you have used the Neovim installation before, it will have created three folders in which to write your files, which are:
    ```
    ~/.config/nvim
    ~/.local/share/nvim
    ~/.cache/nvim
    ```

- To perform a clean installation of the configuration, we need to back up the previous one first:
    ```
    mkdir ~/backup_nvim
    cp -r ~/.config/nvim ~/backup_nvim
    cp -r ~/.local/share/nvim ~/backup_nvim
    cp -r ~/.cache/nvim ~/backup_nvim
    ```

- And then we delete all previous configurations and files:
    ```
    rm -rf ~/.config/nvim
    rm -rf ~/.local/share/nvim
    rm -rf ~/.cache/nvim
    ```

## Installation
Neovim is highly customizable with ability to add plugins. However, it can be daunting for new user to work with this. 

NvChad is a blazing fast Neovim config providing solid defaults and a beautiful UI.
You can install basic plugins and start working with IDE-like neovim within minutes with NvChad.

To install, clone this config:
```
git clone https://github.com/NvChad/NvChad ~/.config/nvim --depth 1 && nvim
```

The first part of the command clones the NvChad repository to the `~/.config/nvim` folder; this is Neovim's default path for searching the user configuration.
The `--depth 1` option instructs git to clone only the repository set as `default` on GitHub.

Once the cloning process is finished in the second part of the command, the Neovim executable (`nvim`) is called, which upon finding a configuration folder will start importing the configurations encountered in the `init.lua` files of it in a predefined order.

## Neovim Keymap
- To change theme: `Space` > `t` > `h`
- Install language parser using: `:TSInstall <language>`. For example, `:TSInstall python`. More list on official website.


# Using NeoVim
Youtube:
    - https://www.youtube.com/watch?v=Mtgo-nP_r8Y
    - https://www.youtube.com/watch?v=GEHPiZ10gOk
Fonts: https://www.jetbrains.com/lp/mono/

## Install language parser
```
Enter :TSInstall followed by the name of the language you want to install
Example: :TSInstall python
```

## Install Debugger
```
Enter :DapInstall followed by the name of the debugger you want to install
Example: :DapInstall python
```
