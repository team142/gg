# Good Game - A game with no name

[![Build Status](https://travis-ci.org/team142/gg.svg?branch=master)](https://travis-ci.org/team142/gg)
[![Pending Pull-Requests](http://githubbadges.herokuapp.com/team142/gg/pulls.svg?style=flat)](https://github.com/team142/gg/pulls)
[![Github Issues](http://githubbadges.herokuapp.com/team142/gg/issues.svg?style=flat)](https://github.com/team142/gg/issues)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](http://badges.mit-license.org)


The goal of this project is to build an MMO tank game implemented in JS with Babylon.js and Java.

## Progress & Planning
<a href="https://trello.com/b/kTa2O4Ya/gg-board"><img src="docs/trello.png" height="80"></a>


## Running the application

For the server you will need Java 8, maven and docker. There are two ways you can run the the project:

### Build and run

Run `mvn clean install`. Maven will retrieve dependencies. The project does require Lombok and Jackson in the project to run. 
The server can be run in a JEE 7 compliant web container. The server serves the client application written in html + js.

### Build and in a docker image

Run `./run.sh` or `./build.bat`. This will remvoe any references to the docker image in your local registry, use maven to build the war, build a docker image and run the image exposing the application locally on port 8080.


## Deploying from Docker Hub

While the game has not yet reached beta you can follow our progress by using images in the official Docker Hub registry:

[Official Docker Hub page](https://hub.docker.com/r/team142/gg/tags/)

## Discord channel

Any feedback would be appreciated:
[Discord Server](https://discord.gg/XPzJSR)
