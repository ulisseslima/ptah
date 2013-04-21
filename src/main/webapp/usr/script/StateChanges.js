/**
 * Callback function for Login function on LoginWindow.js.
 */
function loginResponse(){
    if(responseIsReady()){
        if(responseCode() == "success"){
            appendReplaceableMessage(getResponseMessage(), responseCode(), "txtPass", "loginCheck");
            //setTimeout("promptWindow.close()", 4000);
            document.location.href = "./Accounts";
        }else{
            $("txtPass").value = "";
            var txtUser = $("txtUser");
            txtUser.focus();
            txtUser.select();
            appendReplaceableMessage(getResponseMessage(), responseCode(), "txtPass", "loginCheck");
        }
    }
}

/**
 * Ajax Callback for <code>requestLocalizedMessage()</code>.
 */
function getLocalizedMessage(){
    if(responseIsReady()){
        window[responseCode()] = getResponseMessage();
    }
}

/**
 * Ajax Callback for <code>requestLocalizedMessages()</code>.
 */
function getLocalizedMessages(){
    if(responseIsReady()){
        localizedMessages += getRawResponse();
    }
}