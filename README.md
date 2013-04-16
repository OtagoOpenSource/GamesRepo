# Codename "Satellite"

"Satellite" is a rapid paced game where you destroy planets using an extensive assortment
of dangerous cosmic weaponry to direct your enemy planets to their doom whether it be a blackhole or another planet.

[![Satellite Menu Screenshot](http://timatooth.com/static/images/satellite/satellite-menu.png)](http://timatooth.com/static/image/satellite/satellite-menu.png)

All project ideas, features, artwork etc are discussed in the 
[Wiki](https://github.com/OtagoOpenSource/GamesRepo/wiki).
Anyone should be able to add/edit pages.

## Download the game for testing
[![Linux](http://timatooth.com/static/images/os-icons/linux-icon.png)](http://timatooth.com:8080/job/Otago%20Open%20Source%20Graphics/lastSuccessfulBuild/artifact/dist/Satellite-Linux.zip)
[![Mac](http://timatooth.com/static/images/os-icons/mac-icon.png)](http://timatooth.com:8080/job/Otago%20Open%20Source%20Graphics/lastSuccessfulBuild/artifact/dist/Satellite-MacOSX.zip)
[![Windows](http://timatooth.com/static/images/os-icons/windows-icon.png)](http://timatooth.com:8080/job/Otago%20Open%20Source%20Graphics/lastSuccessfulBuild/artifact/dist/Satellite-Windows.zip)

Pick your platform above to start download. These are the very latest 'builds' of the game and might be very buggy
or completely lack vital features.

Current latest build status:
[![Build Status](http://timatooth.com:8080/job/Otago%20Open%20Source%20Graphics/badge/icon)](http://timatooth.com:8080/job/Otago%20Open%20Source%20Graphics/)

## Development Build Instructions (in jMonkey IDE)

1. Please fork the project first. ```git clone https://github.com/[Git_Username]/GamesRepo.git```

2. In jMonkey open the project; File -> Open Project (navigate to the directory where you cloned the code.)

3. The game project should be in the projects menu on the right. You should be able to select it and run it.

## Build Instructions (in Command Line) [not recommended]
This method can be quite error prone. There might be a better way to do it to simply the build command. 
You need to supply ant the path where you have saved the engine.

1. Clone files.
2. Download the jmonkey engine libraries zip file [nightly builds](http://jmonkeyengine.com/nightly/) and extract the zip.
3. ```cd``` into GamesRepo folder.
4. Run ```ant -Dlibs.jme3.dir=/path/to/extractedzipdir/lib -Duser.properties.file=jme-project-libs.properties```
5. Results such as the game.jar and javadoc are put into the ```dist/``` folder.
