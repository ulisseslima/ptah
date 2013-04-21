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