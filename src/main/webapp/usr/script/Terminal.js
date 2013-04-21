var RETURN_KEY = 13;

/**
 * Checks whether a given Key is pressed.
 */
function key_pressed(keyCode, event){
    var characterCode; //literal character code will be stored in this variable
    if(event && event.which){ //if which property of event object is supported (NN4)
        event = event;
        characterCode = event.which; //character code is contained in NN4's which property
    }else{
        event = event;
        characterCode = event.keyCode; //character code is contained in IE's keyCode property
    }
    if(characterCode == keyCode){ //if generated character code is equal to ascii 13 (if enter key)
        return true;
    }else{
        return false;
    }
}

/**
 * Controls commands given by the user.
 * TODO define maximum log lines OR maximize the size of the console area and put a scrollbar.
 * @command The command input object.
 */
function sendCommand(command){
    if(command.value !== "" && key_pressed(RETURN_KEY, event)){
        var log = $("console-log");
        log.innerHTML += "<h5>"+command.value+"</h5>";
        command.value = "";
        log.scrollTop = log.scrollHeight;
    }
}

/**
 * @action whether to hide or show the prompt
 */
function prompt(action){
    var console = $("console");
    if(action == "show"){
        console.style.bottom = "31px";
        console.style.opacity = ".8";
    } else{
        console.style.bottom = "60px";
        console.style.opacity = "0";
    }
}