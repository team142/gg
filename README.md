# Good Game - A game with no name

[![Build Status](https://travis-ci.org/team142/gg.svg?branch=master)](https://travis-ci.org/team142/gg)
[![Pending Pull-Requests](http://githubbadges.herokuapp.com/team142/gg/pulls.svg?style=flat)](https://github.com/team142/gg/pulls)
[![Github Issues](http://githubbadges.herokuapp.com/team142/gg/issues.svg?style=flat)](https://github.com/team142/gg/issues)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](http://badges.mit-license.org)


The goal of this project is to a MMO tank game with tanking getting special upgrades implemented in Java & JS with Babylon.js

## Road map
[Trello Roadmap](https://trello.com/b/kTa2O4Ya/good-game)


## Building the server (client inside)

For the server you will need Java 8 and maven.

Run `mvn clean install`. Maven will retrieve dependencies. The project does require Lombok and Jackson in the project to run. 

The server can be run in a JEE 7 compliant web container. The server serves the client application written in html + js.



## Building a docker image

There is an included Dockerfile.
Simply run: `mvn clean install` and `docker build -t team142l/gg:version` to build a docker image using the target directory's war.


## Running without building

TODO (Docker hub images link)

## Features


### Complete & tested

- TODO


### Completed but not fully tested

- TODO


### Planned

- TODO
