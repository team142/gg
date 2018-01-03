

export  function View(){


    function changeView(view) {
        console.log("Chaning view: " + view)
        toggleElement("VIEW_SERVERS", view == "VIEW_SERVERS")
        toggleElement("VIEW_GAMES", view == "VIEW_GAMES")
        toggleElement("VIEW_CANVAS", view == "VIEW_CANVAS")
    
        if (view == "VIEW_CANVAS") {
            console.log("Setup 3D");
            setup3D();
        }
    
    }
    
    
    function toggleElement(id, toggle) {
        if (toggle) {
            document.getElementById(id).style.display = "block"
            document.getElementById(id).style.visibility = "visible";
        } else {
            document.getElementById(id).style.visibility = "hidden";
            document.getElementById(id).style.display = "none"
        }
    
    }

    return {
        changeView: changeView
    }


}