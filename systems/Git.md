# Git Config
## Global and Local username/email address

# Syncing
## Updating current branch from another branch
Assume you make a new branch for your code called `feature1` branch from `develop` branch.
```
$ git branch 
      develop
    * feature1
```

While you have been working on `feature1`, a lot of the changes pushed to the `develop` branch. How do you update the current branch `feature1` with the latest commits at `develop` one? 

You just merge develop to `feature1`:
```
git checkout feature1
git merge develop
```

## Updating forks

## Deleting branch
If you just want to learn the correct incantation to delete branches in Git, we’ll start by offering the TL;DR version.
To delete a local branch in Git, you simply run:
```
git branch -d <branch-name>
```

If the branch contains unmerged changes, though, Git will refuse to delete it. If you’re sure you want to do it, you’ll have to force the deletion by replacing the -d parameter with an uppercase D:
```
git branch -D <branch-name>
```

You don’t use the git branch command to delete a remote branch. You use git push, even if that sounds weird. Here’s how you do it:
```
git push --delete <remote name> <branch name>
```

It’s like you’re pushing—sending—the order to delete the branch to the remote repository.

## Compare local git branch with remote
Use the following command:
```
git diff <local branch> <remote>/<remote branch>
```

//TODO
- Remote url
- ssh keys for multiple accounts
- 