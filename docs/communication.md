# Server to client communication



### Change view
````
    {
        "conversation": "S_CHANGE_VIEW",
        "view": "VIEW_SERVERS|VIEW_GAMES|VIEW_CANVAS"
    }
````

### List of games
````
    {
        "conversation": "S_LIST_OF_GAMES",
        "GAMES": [
            {
                "id": "$UUID$",
                "name": "$GAME_NAME$",
                "numberPlaying": 10
            }
        ]
    }
````

### Here is the map
````
    {
        "conversation": "S_SHARE_MAP",
        ... to do
    }
````

### Scoreboard
````
    {
        "conversation": "S_SCOREBOARD",
        "view": "VIEW_SERVERS|VIEW_GAMES|VIEW_CANVAS"
    }
````

### Moving things positions
````
    {
        ...
    }
````

### Static things positions
````
    {
        ...
    }
````


# Client to server communication

### Join game
````
    {
        "conversation": "P_REQUEST_JOIN_GAME",
        "id": "$GAME_UUID$"
    }
````


