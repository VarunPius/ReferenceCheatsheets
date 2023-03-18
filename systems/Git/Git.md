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

# Restoring deleted files
## Deleted a file but didn’t commit
So you deleted a file, and immediately realized it was a mistake? This one is easy, just do:
```
$ git checkout HEAD <filename>
```
This will work regardless of whether the deletion was staged or not.

## Deleted a file and committed the deletion
You made a commit deleting a file, but then realized you wanted to keep the file around after all? Do a reset to go back to the state before your commit (be careful: the `--hard` option means the command will discard changes to tracked files after the specified commit — you can also leave this option out in which case the file deletion will show up as an unstaged change along with any other changes you’ve made in your working tree. The file can then be restored as in the previous scenario):
```
$ git reset --hard HEAD~1
```
> Note: this presumes you haven’t already pushed your commit to a remote

## Committed the deletion and then I did more commits
If you deleted a file, committed, then continued work and did more commits, only to find that deleting the file was a mistake, Git still has you covered! To find the right commit, first check the history for the deleted file:
```
$ git log -- <filename>
```
You can either work with the last commit that still had the file, or the commit that deleted the file. In the first case, just checkout the file from that commit:
```
$ git checkout <commit hash> -- <filename>
```
In the second case, checkout the file from one commit before that:
```
$ git checkout <deletion commit hash>~1 -- <filename>
```

# Deleted a file, committed and pushed
If you’ve already pushed your commit or commits to a remote, resetting and pushing again will cause problems, as the history of the local repository has essentially been rewritten. In this case, it is probably better to record a new commit which undoes the work of the one deleting the file. To do this, run:
```
$ git revert --no-commit <commit>
```
Above, `<commit>` is the commit deleting the file. Following this, create your new commit as desired. The "--no-commit" option prevents the command from creating a new commit right away, instead allowing you to choose exactly which of the changes introduced in the old commit you want to revert in your new commit.

