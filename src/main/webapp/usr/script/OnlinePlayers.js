//tentar criar o objeto player e renomear essa classe pra Player.js
var parentElement = $("char-container");
var list = "online.none;char.info.lastMap";
requestLocalizedMessages(list);

function requestOnlinePlayers(){
    setLoadingStatus($("char-container"));
    var url = "./OnlinePlayers?action=check";
    createRequest(url, "getOnlinePlayers");
}

function getOnlinePlayers(){
    if(responseIsReady()){
        dropChildren(parentElement);
        removeElement("playersCheck");
        var players = getRawResponse().split(";");
        for(var i in players){
            appendNewPlayer(players[i].split(","));
        }
    }else{
        dropChildren(parentElement);
        appendReplaceableMessage(getMessage("none"), "warning", "char-container", "playersCheck");
    }
}

function appendNewPlayer(player){
    var element = document.createElement("div");
    element.className = "trans char-info";
    element.innerHTML = "<b>" + player[1] + ", the " + player[0] + ":</b> " + player[2] + "<br>" + getMessage("lastMap") + " " + player[3];
    parentElement.appendChild(element);
}