# Good Game - A game with no name

[![Build Status](https://travis-ci.org/team142/gg.svg?branch=master)](https://travis-ci.org/team142/gg)
[![Docker Pulls](https://img.shields.io/docker/pulls/team142/gg.svg?maxAge=86400)](https://github.com/team142/gg)
[![Microbadger](https://images.microbadger.com/badges/image/team142/gg.svg)](http://microbadger.com/images/team142/gg "Image size")
[![Pending Pull-Requests](http://githubbadges.herokuapp.com/team142/gg/pulls.svg?style=flat)](https://github.com/team142/gg/pulls)
[![Github Issues](http://githubbadges.herokuapp.com/team142/gg/issues.svg?style=flat)](https://github.com/team142/gg/issues)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](http://badges.mit-license.org)


The goal of this project is to build an MMO tank game implemented in JS with Babylon.js and Java.

## Planning & progress
<a href="https://trello.com/b/kTa2O4Ya/gg-board"><img src="docs/trello.png" height="80"></a>

## Setup for development

- Install Java 8
- Install Docker
- Install rollup `npm install rollup -g`
- Install uglify-es `npm install uglify-es -g`
- Install an http file server `npm install http-server -g`
- Install gradle (ensure gradle is in path)

## Running the client only

- Install rollup `npm install rollup -g`
- Install an http file server `npm install http-server -g`
- Go to the client directory `cd client/`
- Run `http-server`
- Navigate to <a href="http://localhost:8080">localhost:8080</a>
- For the server drop-down, select team142.co.za

## Running the client & the server

For the server you will need Java 8, gradle (in path) and docker. There are two ways you can run the the project:

You will now be running the client locally and connecting to the shared development server.

### Build and run locally in docker (easy and fast)

Run `./run.sh` or `./run.bat`. This will remove any references to the docker image in your local registry, use gradle to build the jar, build a docker image and run the image exposing the application locally on port 8080. See the source of those files for more details.

### Build and run in your own Java servlet container

Run `gradle bootRun`. Deploy the resulting war to a JEE 8 compliant web container running Java 8. 

## Official Docker images
<a href="https://hub.docker.com/r/team142/gg/tags/"><img src="docs/docker.png" height="80"></a>

## Community (Dev, Q&A & suggestions)
<a href="https://discord.gg/QaagkDh"><img src="docs/discord.png" height="80"></a>
