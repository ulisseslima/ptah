requestLocalizedMessages("request.NewGroup;groups.Title");
var groups = $("groups");
var requestElementId;

function showGroups(requestId){
    requestElementId = "request-".concat(requestId);
    var parent = $(requestElementId);
    if(!groups.hasChildNodes()){
        requestGroups();
    }
    parent.appendChild(groups);
}
function acceptRequest(requestId){
    setLoadingStatus($("btnRequest".concat(requestId)));
    createRequest(
        "./User?action=acceptFriend&requestId=" + requestId + "&groups=" + getSelectedGroups(),
        "response_friendRequest");
}
/**
 * Callback for acceptRequest().
 */
function response_friendRequest(){
    processResponse("request-");
}
function ignoreRequest(requestId){
    createRequest(
        "./User?action=rejectFriend&requestId=" + requestId,
        "response_ignoreRequest");
}
function response_ignoreRequest(){
    processResponse("request-");
}
function processResponse(reqElementId){
    if(responseIsReady()){
        appendMessage(getResponseMessage(), responseCode(), reqElementId.concat(getResponseId()));
        removeElement(reqElementId.concat(getResponseId()));
    }
}
/**
 * Callback for requestGroups()
 */
function getGroups(){
    if(responseIsReady()){
        setGroups();
        groupContainer.appendChild(newGroup());
        groups.appendChild(groupContainer);
        groupNames = getRawResponse();
    }
}