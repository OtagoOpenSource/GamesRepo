# Otago Open Source Game Project

Welcome to the Otago Open Source game project! All project ideas, features, artwork etc are discussed in the [GitHub wiki](https://github.com/OtagoOpenSource/GamesRepo/wiki). Anyone should be able to add/edit pages.

## Build Instructions (in jMonkey IDE)

1. Please fork the project first. ```git clone https://github.com/[Git_Username]/GamesRepo.git```

2. In jMonkey open the project; File -> Open Project (navigate to the directory where you cloned the code.)

3. The game project should be in the projects menu on the right. You should be able to select it and run it.

## Build Instructions (in Command Line)
This method can be quite error prone. There might be a better way to do it to simply the build command. 
You need to supply ant the path where you have saved the engine.

1. Clone files.
2. Download the jmonkey engine libraries zip file [nightly builds](http://jmonkeyengine.com/nightly/) and extract the zip.
3. ```cd``` into GamesRepo folder.
4. Run ```ant -Dlibs.jme3.dir=/path/to/extractedzipdir/lib -Duser.properties.file=jme-project-libs.properties```
5. Results such as the game.jar and javadoc are put into the ```dist/``` folder.
