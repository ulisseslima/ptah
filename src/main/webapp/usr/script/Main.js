var loadStart = new Date().valueOf();
window.onload = function() {
    var loadEnd = new Date().valueOf();
    var pageSpeedCookie = "PageSpeedCookie="+document.title+"@"+(loadEnd - loadStart);
    pageSpeedCookie += "; expires=Fri, 27 Jul 2013 02:47:11 UTC; path=/";
    document.cookie = pageSpeedCookie;
};
var localizedMessages = "";
var highestZ = 1;
var loading = "usr/style/loading1.gif";

String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g, "");
};
var MINIMUM_LENGTH = 3;

function removeFunnyCharacters(string){
    return string.replace(/[^a-zA-Z0-9 àèìòùáéíóúãõâêîôûäëïöü\-ÀÈÌÒÙÁÉÍÓÚÃÕÂÊÎÔÛÄËÏÖÜ.]/g, "");
}
/**
 * Removes semicolons and tag delimiters from a String. Useful for creating a CSV String from an HTML form.
 * @param string The String to be escaped
 */
function fix(string){
    return string.replace(";", "").replace("<", "").replace(">", "");
}
/**
 * Appends a message embedded in a H3 element and removes any busy indicator it finds.
 * @param message The message to be embedded;
 * @param severity The severity level of the message. Also the CSS className used to display the message;
 * @param parentId The id of the parent element.
 */
function appendMessage(message, severity, parentId){
    var parent = $(parentId);
    var messageElement = document.createElement("h3");
    messageElement.className = severity;
    messageElement.innerHTML = message;
    endLoading(parentId);
    insertAfter(parent, messageElement);
}

/**
 * Appends a message embedded in a H3 element and removes any busy indicator it finds.
 * It also replaces the previous message.
 * @param message The message to be embedded;
 * @param severity The severity level of the message. Also the CSS className used to display the message;
 * @param parentId The id of the parent element;
 * @param replaceableId The id of the message element to be replaced.
 */
function appendReplaceableMessage(message, severity, parentId, replaceableId){
    removeElement(replaceableId);
    var parent = $(parentId);
    endLoading(parentId);
    insertAfter(parent, createInfoMessage(replaceableId, severity, message));
}

/**
 * Removes an element from the page.
 * @param elementId
 */
function removeElement(elementId){
    var element = $(elementId);
    if(element && element.parentNode){
        element.parentNode.removeChild(element);
    }
}

/**
 * Creates an info element on the page.
 * @param id The element id;
 * @param severity The severity of the message can be: success, warning or fail;
 * @param message The info content.
 */
function createInfoMessage(id, severity, message){
    var messageElement = document.createElement("h3");
    messageElement.id = id;
    messageElement.className = severity;
    messageElement.innerHTML = message;
    return messageElement;
}

/**
 * If the parents lastchild is the targetElement, it adds the <code>newElement</code> after the <code>targetElement</code>.
 * Otherwise, the target has siblings, then insert the <code>newElement</code> between the <code>targetElement</code> and it's next sibling.
 * @param newElement The element to insert in the page;
 * @param targetElement What you want <code>newElement</code> to go after. Look for this elements parent.
 */
function insertAfter(targetElement, newElement) {
    var parent = targetElement.parentNode;
    if(parent.lastchild == targetElement) {
        parent.appendChild(newElement);
    } else {
        parent.insertBefore(newElement, targetElement.nextSibling);
    }
}

/**
 * Appends an element containing a "busy status" indicator.
 * @param parent The element where the indicator will be appended to.
 */
function setLoadingStatus(parent){
    if(!$("loading_".concat(parent.id))){
        insertAfter(parent, createLoadingElement(parent.id));
    }
}

function createLoadingElement(id){
    var busyIndicator = document.createElement("div");
    busyIndicator.id = "loading_" + id;
    busyIndicator.className = "busy";
    return busyIndicator;
}

/**
 * Discover if the given element has a loading element appended to it.
 * @param elementId
 * @return <code>true</code> if a loading element with <code>elementId</code> can be found;
 * <code>false</code> otherwise.
 */
function endLoading(elementId){
    removeElement("loading_" + elementId);
}

/**
 * Checks if the form has any blank text inputs.
 */
function checkForm(submitButtonId){
    var fields = document.getElementsByTagName("input");
    for(var i in fields){
        if(fields[i].className == "required-field"){
            var value = fields[i].value.trim();
            if(value == "" || value.length < MINIMUM_LENGTH){
                alert(getMessage("allFieldsRequired"));
                return false;
            }
        }
    }
    prepareSubmit(submitButtonId);
    return true;
}

/**
 * Selects a message from the localizedMessages list.
 * The list is built in the following format: "key1'at'value 1;key2'at'value 2"...
 * @param key The key name of the desired message.
 */
function getMessage(key){
    var list = localizedMessages.split(";");
    for(var i in list){
        var pair = list[i].split("@");
        if(pair[0] == key){
            return pair[1];
        }
    }
    alert("Key '" + key + "' was not found in " + localizedMessages);
    return "Key not found";
}

/**
 * Replaces a submit button with a loading indicator.
 * @param submitButtonId The button object.
 */
function prepareSubmit(submitButtonId){
    var submitButton = $(submitButtonId);
    setLoadingStatus(submitButton);
    submitButton.disabled = false;
}

/**
 * Removes all children from a parent element
 * @param element The parent element.
 */
function dropChildren(element){
    if (element.hasChildNodes()){
        while (element.childNodes.length >= 1){
            element.removeChild(element.firstChild);
        }
    }
}

/**
 * @return a DOM object by the specified id
 */
function $(elementId){
    return document.getElementById(elementId);
}
/**
 * Limits the number of characters a client is able to enter in a textarea,
 * shows the remaining characters if it reaches 90% of the max length,
 * and automatically sets the height of the element according to the text entered.
 * @param element Any element with a maxlength attribute.
 */
function limit(element){
    var maxLength = parseInt(element.getAttribute("maxlength"));
    if(element.value.length > (maxLength * 90) / 100){
        $("char-count").style.visibility = "visible";
        $("char-count").innerHTML = maxLength - element.value.length;
    }else{
        $("char-count").style.visibility = "hidden";
    }
    if (element.value.length>maxLength){
        element.value=element.value.substring(0,maxLength);
    }
    if(element.scrollHeight){
        while (element.scrollHeight > element.clientHeight) {
            element.rows++;
        }
    }
}

/**
 * Collapses an entire element, showing only the first 25 pixels.
 * If the element was already collapsed, it is expanded.
 */
function collapse(element){
    var body = element.parentNode;
    if(body.style.height == "25px"){
        body.style.height = "";
    }else{
        body.style.height = "25px";
    }
}