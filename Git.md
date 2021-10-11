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
