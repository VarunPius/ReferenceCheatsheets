# Basic setup
## Generating a new SSH key
- Open Terminal
- Create the SSH key as follows:
  ```
  $ ssh-keygen -t ed25519 -C "your_email@example.com"
  ```
  > Note: If you are using a legacy system that doesn't support the Ed25519 algorithm, use:
  > ```
  > $ ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
  > ```

  This creates a new SSH key, using the provided email as a label.
  ```
  > Generating public/private ALGORITHM key pair.
  ```

  When you're prompted to `Enter a file in which to save the key`, you can press `Enter` to accept the default file location. Please note that if you created SSH keys previously, `ssh-keygen` may ask you to rewrite another key, in which case we recommend creating a custom-named SSH key. To do so, type the default file location and replace `id_ssh_keyname` with your custom key name.
  ```
  > Enter a file in which to save the key (/Users/YOU/.ssh/id_ALGORITHM: [Press enter]
  ```
- At the prompt, type a secure passphrase.
  ```
  > Enter passphrase (empty for no passphrase): [Type a passphrase]
  > Enter same passphrase again: [Type passphrase again]
  ```


## Adding your SSH key to the ssh-agent
Before adding a new SSH key to the `ssh-agent` to manage your keys, you should have checked for existing SSH keys and generated a new SSH key. When adding your SSH key to the agent, use the default macOS `ssh-add` command, and not an application installed by `macports`, `homebrew`, or some other external source.
- Start the ssh-agent in the background.
  ```
  $ eval "$(ssh-agent -s)"
  > Agent pid 59566
  ```
  Depending on your environment, you may need to use a different command.
  For example, you may need to use root access by running `sudo -s -H` before starting the `ssh-agent`, or you may need to use `exec ssh-agent bash` or `exec ssh-agent zsh` to run the `ssh-agent`.

- If you're using macOS Sierra 10.12.2 or later, you will need to modify your `~/.ssh/config` file to automatically load keys into the ssh-agent and store passphrases in your keychain. First, check to see if your `~/.ssh/config` file exists in the default location.
  ```
  $ open ~/.ssh/config
  > The file /Users/YOU/.ssh/config does not exist.
  ```

  If the file doesn't exist, create the file.
  ```
  $ touch ~/.ssh/config
  ```

  Open your `~/.ssh/config` file, then modify the file to contain the following lines. If your SSH key file has a different name or path than the example code, modify the filename or path to match your current setup.
  ```
  Host github.com
    AddKeysToAgent yes
    UseKeychain yes
    IdentityFile ~/.ssh/id_ed25519
  ```

  > If you chose not to add a passphrase to your key, you should omit the UseKeychain line.
  > If you see a Bad configuration option: usekeychain error, add an additional line to the configuration's' Host *.github.com section.
  > ```
  > Host github.com
  > IgnoreUnknown UseKeychain
  > ```

- Add your SSH private key to the `ssh-agent` and store your passphrase in the keychain. If you created your key with a different name, or if you are adding an existing key that has a different name, replace `id_ed25519` in the command with the name of your private key file.
  ```
  $ ssh-add --apple-use-keychain ~/.ssh/id_ed25519
  ```
  > Note: The `--apple-use-keychain` option stores the passphrase in your keychain for you when you add an SSH key to the ssh-agent. If you chose not to add a passphrase to your key, run the command without the `--apple-use-keychain` option.
  >
  > The `--apple-use-keychain` option is in Apple's standard version of `ssh-add`. In MacOS versions prior to Monterey (12.0), the `--apple-use-keychain` and `--apple-load-keychain` flags used the syntax `-K` and `-A`, respectively.

- Add the SSH key to your account on GitHub.


## Adding a new SSH key to your GitHub account
- Copy the SSH public key to your clipboard.

  If your SSH public key file has a different name than the example code, modify the filename to match your current setup.
  When copying your key, don't add any newlines or whitespace.
  ```
  $ pbcopy < ~/.ssh/id_ed25519.pub
  # Copies the contents of the id_ed25519.pub file to your clipboard
  ```
  > Tip: If `pbcopy` isn't working, you can locate the hidden `.ssh` folder, open the file in your favorite text editor, and copy it to your clipboard.

- Go to Settings and in security settings, you should have an option to add key
- For `Key Type` choose `Authentication Type` if available


# Multiple git accounts
- Create a new ssh-key and add it to the work GitHub account
- Modify the SSH config file (`~/.ssh/config`). Open the config file in a text editor (create it if there isn’t one in the `~/.ssh` folder yet) and add the following to it:
  ```
  # Personal GitHub account
  Host github.com
    HostName github.com
    User git
    AddKeysToAgent yes
    UseKeychain yes
    IdentityFile ~/.ssh/id_ed25519

  # Work GitHub account
  Host github-work
    HostName github.com
    User git
    AddKeysToAgent yes
    UseKeychain yes
    IdentityFile ~/.ssh/id_ed25519_work
  ```
- Clone work repo with a slightly different query as follows:
  ```
  # Instead of this:
  git@github.com:[my work GitHub group]/[my project].git

  # We need to tweak its address like this before we can git clone it:
  git@github-work:[my work GitHub group]/[my project].git
  ```

- From now on, to ensure that our commits and pushes from each repository on the system uses the correct GitHub user — we will have to configure `user.email` and `user.name` in every repository freshly cloned or existing before.

  To do this use the following commands.
  ```
  git config user.email "my_office_email@gmail.com"
  git config user.name "Varun Pius Rodrigues"

  git config user.email "my-personal-email@gmail.com"
  git config user.name "Varun Pius Rodrigues"
  ```

  Pick the correct pair for your repository accordingly.

  To push or pull to the correct account we need to add the remote `origin` to the project
  ```
  # Check remote
  git remote -v
  git config -e

  # Add remote
  git remote add origin git@github.com:VarunPius/<repo>.git

  git remote add origin git@github-work:<WorkUser>/<repo>.git

  # Configure remote if already present
  git remote set-url origin git@github-personal:USERNAME/REPOSITORY.git
  ```

- Now you can use:
  ```
  git push

  git pull
  ```

## Debugging
In case you have issues when dealing with private accounts, try the foolowing. That problem might be having with your `ssh-agent`, your SSH key hasn't been added with `ssh-agent`. You have to apply following steps using your terminal:
```sh
# Check git remote config
git remote -v
git remote set-url origin git@github-personal:USERNAME/REPOSITORY.git

eval "$(ssh-agent -s)"
> Agent pid 5867

ssh-add <private-ssh-key-file-name>   # eg. id_rsa (not .pub)
Enter passphrase for /home/you/.ssh/id_rsa: [] Identity added: /home/you/.ssh/id_rsa (/home/you/.ssh/id_rsa)
```

### Configuration:
Go to the repo and check the configurations:
```
git config -e

# If you see [user] section missing, you can include it as follows
git config user.name "Varun Pius Rodrigues"
git config user.email email@gmail.com
```