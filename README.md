# base-app

Base application to get projects off the ground quickly

# TODO
After pulling down this repo to use for a new project, it requires a few changes

1. Change the `R.string.app_name` value to your new app name
2. Change the `rootProject.name` value in `settings.gradle`
3. Change the `applicationId` value in the `app` module `build.gradle`

You will probably also want to setup a new repo to track the changes.
To do that follow the steps below

1. `git remote rename origin upstream-app-template`
2. `git remote add origin <new projet url>`
3. `git branch --set-upstream-to origin/trunk`
4. `git pull --allow-unrelated-histories`
5. Merge any conflicts, then `git push` (this will push to the new repo) 
and you should be all set