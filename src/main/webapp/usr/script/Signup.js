var checkedField;
var fieldId;
requestLocalizedMessages("signup.exception.invalidMail;forms.exception.allFieldsRequired;forms.exception.invalidInput");

/**
 * Checks whether the given value already exists for the given field.
 */
function checkAvailability(field, elementId, servlet){
    checkedField = $(elementId);
    if(checkedField.value.length > 2){
        if(field == "name"){
            checkedField.value = removeFunnyCharacters(checkedField.value.trim());
        }
        if(checkedField.value == ""){
            appendReplaceableMessage(getMessage("invalidInput"), "fail", checkedField.id, fieldId);
        }else{
            setLoadingStatus(checkedField);
            fieldId = field + "Check";
            var url; servlet == null? url = "./Signup" : url = servlet;
            url += "?action=check&field=" + field + "&value=" + checkedField.value;
            createRequest(url, "availabilityResponse");
        }
    }
}

/**
 * Ajax Callback function for <code>checkAvailability</code>.
 */
function availabilityResponse(){
    if(responseIsReady()){
        appendReplaceableMessage(getResponseMessage(), responseCode(), checkedField.id, fieldId);
    }
}

/**
 * @param field
 * @param elementId
 */
function evalEmailAddress(field, elementId){
    var address = $(elementId).value;
    var atPosition = address.indexOf("@");
    var dotPosition = address.lastIndexOf(".");
    if (atPosition < 1 || dotPosition - atPosition < 2 ){
        appendReplaceableMessage(getMessage("invalidMail"), "warning", elementId, "mailCheck");
    } else {
        checkAvailability(field, elementId);        
    }
}

/**
 * Checks if the password confirmation field matches the password field.
 */
function checkMatch(){
    var password1 = $("txtPassword1").value.trim();
    var password2 = $("txtPassword2").value.trim();

    if(password1.length > 4){
        if(password1 == password2){
            appendReplaceableMessage("OK", "success", "txtPassword1", "passwordsMatch");
        }else{
            appendReplaceableMessage("!", "warning", "txtPassword1", "passwordsMatch");
        }
    }else{
        removeElement($("passwordsMatch"));
    }
}