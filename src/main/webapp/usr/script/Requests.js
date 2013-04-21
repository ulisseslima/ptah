requestLocalizedMessages("request.Message;request.NewGroup;groups.Title");

function requestFor(requestType){
    switch(requestType){
        case "friendlist":
            break;
        case "friendship":
            requestGroups();
            break;
        case "party":
            break;
        case "guild":
            break;
    }
}
function unfriend(friendName){
    createRequest(
        "./User?action=unfriend&id=".concat(friendName),
        "response_unfriend");
}
/**
 * Callback for unfriend()
 */
function response_unfriend(){
    if(responseIsReady()){
        appendMessage(getResponseMessage(), responseCode(), "btnUnfriend");
    }
}
function confirmRequest(requestType, subjectId){
    switch(requestType){
        case "friendlist":
            break;
        case "friendship":
            setLoadingStatus($("groups"));
            createRequest(
                "./User?action=friend&groups=" + getSelectedGroups() + "&id=" + subjectId + "&message=" + $("request-message").value,
                "response_friendRequest");
            dropChildren($("groups"));
            dropChildren($("confirm-request"));
            break;
        case "party":
            break;
        case "guild":
            break;
    }
}

function response_friendRequest(){
    if(responseIsReady()){
        appendReplaceableMessage(getResponseMessage(), responseCode(), "groups", "response_friendRequest");
    }
}

/**
 * Callback for requestGroups()
 */
function getGroups(){
    if(responseIsReady()){
        $("btnFriendship").style.display = "none";
        setMessageArea();
        setGroups();
        groupContainer.appendChild(newGroup());
        $("groups").appendChild(groupContainer);
        $("confirm-request").style.display = "block";
        $("request-message").focus();
        groupNames = getRawResponse();
    }
}
function setMessageArea(){
    var label = newLabel(getMessage("Message"));
    label.className = "groups-title";
    groupContainer.appendChild(label);
    groupContainer.appendChild(newTextArea());
}
function newTextArea(){
    var txtArea = document.createElement("textarea");
    txtArea.id = "request-message";
    txtArea.style.rows = "4";
    txtArea.style.width = "90%";
    return txtArea;
}