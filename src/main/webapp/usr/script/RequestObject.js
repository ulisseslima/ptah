var request = null;
var response = null;
/**
 * The default string for successful responses
 */
var SUCCESS = "success";
/**
 * The default string for failed responses
 */
var FAIL = "fail";

function getLocale(){
    if(navigator.language){
        return navigator.language;
    }else if(navigator.browserLanguage){
        return navigator.browserLanguage;
    }else{
        return null;
    }
}
/**
 * @return the unmodified response string.
 */
function getRawResponse(){
    return response;
}
/**
 * If the response sent from the Servlet was a combination of a code and a message,
 * this method can be used to obtain the "code" part of the response.
 */
function responseCode(){
    return response.split(";")[0];
}
/**
 * If the response sent from the Servlet was a combination of a code and a message,
 * this method can be used to obtain the "message" part of the response.
 */
function getResponseMessage(){
    return response.split(";")[1];
}
/**
 * Any content not csv-safe should be returned with the "_dvlmsg:" prefix, ensuring it will be returned in its integrity.
 */
function getRichResponse(){
    return response.split("_dvlmsg:")[1]
}
/**
 * The response id is used to ensure the response will be shown in the right element.
 * (considering a scenario where requests can be made concurrently from an array of elements and the response must to be shown in
 * the respective element)
 */
function getResponseId(){
    return response.split(";")[2];
}
/**
 * Uses Cube's own separator token to turn a response into an array.
 */
function getResponseAsList(){
    return getResponseAsCSV(":obj_");
}
function getResponseAsCSV(separator){
    return response.split(separator == null? ";" : separator);
}
/**
 * Creates an Ajax request.
 */
function createRequest(url, stateChangeFunction) {
    try {
        request = new XMLHttpRequest();
    } catch (trymicrosoft) {
        try {
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (othermicrosoft) {
            try {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (failed) {
                request = null;
            }
        }
    }
    if(request == null) {
        alert("Error creating request object!");
    } else {
        request.open("get", url, true);
        request.onreadystatechange = window[stateChangeFunction];
        request.setRequestHeader("Accept-Language", getLocale());
        request.send(null);
    }        
}

function responseIsReady(){
    if(request.readyState == 4){
        if(request.status == 200){
            response = request.responseText;
            return true;
        }
    }
    return false;
}

/**
 * Requests a localized list of messages; The keys are sent as a String, separated by semi-colons.
 * @param list The list of keys.
 */
function requestLocalizedMessages(list){
    createRequest("./I18n?keys=" + list, "getLocalizedMessages");
}