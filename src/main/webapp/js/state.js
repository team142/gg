
export  function UserState(){
    var username;
    return {
        username: username
    }
}

export  function GameState(){
    let map = new Map([["key1", "value1"], ["key2", "value2"]]);
    map.clear();

    return {
        map: map
    }
}
