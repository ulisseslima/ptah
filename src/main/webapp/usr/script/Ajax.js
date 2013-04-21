function AjaxRequest() {
    var request = null;
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
    if(request == null) alert("Error creating request object!");
    return request;
}

function Ajax() {
    this.request = new AjaxRequest();
    /**
     * the response string.
     */
    this.response = null;
    /**
     * Opens an Async Ajax Get Request.
     */
    this.get = function(url, callbackFunction) {
        this.request.open("get", url, true);
        this.request.onreadystatechange = window[callbackFunction];
        this.request.setRequestHeader("Accept-Language", Locale.get());
        this.request.send(null);
    };
    /**
     * Opens an Async Ajax Post Request.
     */
    this.post = function(url, callbackFunction, params) {
        this.request.open("post", url, true);
        this.request.onreadystatechange = window[callbackFunction];
        this.request.setRequestHeader("Accept-Language", Locale.get());
        this.request.send(params);
    };        
    /**
     * If the response sent from the Servlet was a combination of a code and a message,
     * this method can be used to obtain the "code" part of the response.
     */
    this.responseCode = function() {
        return this.response.split(";")[0]
    };
    /**
     * If the response sent from the Servlet was a combination of a code and a message,
     * this method can be used to obtain the "message" part of the response.
     */
    this.responseMessage = function() {
        return this.response.split(";")[1]
    };
    /**
     * Any content not csv-safe should be returned with the "_dvlmsg:" prefix, ensuring it will be returned in its integrity.
     */
    this.richResponse = function() {
        return this.response.split("_dvlmsg:")[1]
    };
    /**
     * The response id is used to ensure the response will be shown in the right element.
     * (considering a scenario where requests can be made concurrently from an array of elements and the response must to be shown in
     * the respective element)
     */
    this.responseId = function() {
        return this.response.split(";")[2];
    };    
    /**
     * Uses Cube's own separator token to turn a response into an array.
     */
    this.responseAsList = function() {
        return getResponseAsCSV(":obj_");
    };
    this.responseAsCSV = function(separator) {
        return this.response.split(separator == null? ";" : separator);
    };
    this.asJson = function() {
        return JSON.parse(this.response);
    };
    this.responseIsReady = function() {
        if(this.request.readyState == 4){
            if(this.request.status == 200){
                this.response = this.request.responseText;
                return true;
            }
        }
        return false;
    }
}

function AjaxResponse() {}
AjaxResponse = {
    /**
     * Default String for successful responses.
     */
    SUCCESS: "success",
    /**
     * Default String for unsuccessful responses.
     */
    FAIL: "fail"
};

function Locale() {}
Locale = {
    get: function() {
        if(navigator.language) {
            return navigator.language;
        } else if(navigator.browserLanguage) {
            return navigator.browserLanguage;
        } else {
            return null;
        }
    }
};
/**
 * Requests a localized list of messages; The keys are sent as a String, separated by semi-colons.
 * @param list The list of keys.
 */
Locale.getLocalizedMessages = function(list) {
    new Ajax().get("./I18n?keys=" + list, "getLocalizedMessages");
}