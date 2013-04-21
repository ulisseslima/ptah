addOnClickHandlers();

var NUM_CHARS = $("NUM_CHARS").value;
var charPosition = 1;
var accountOrder = $("account-order");
var btnSubmit = $("btnReorder");
var reorderElement = $("new-order");
var accountId = accountOrder.value;

function addOnClickHandlers() {
    var charContainer = $("char-container");
    var chars = charContainer.getElementsByTagName("div");
    for (var i in chars) {
        if(chars[i].className == "trans char-info"){
            chars[i].onclick = reorder;
        }
    }
}

function reorder() {
    var charElement = this;
    reorderElement.parentNode.style.display = "block";

    if (charPosition > NUM_CHARS) {
        alert("You already have " + NUM_CHARS + " chars. Click \"Start Over\" to try again.");
        return;
    }
    accountOrder.value += ";" + charElement.id + ":" + (charPosition - 1);
    reorderElement.appendChild(charElement);
    charElement.onclick = null;

    var newSpanElement = document.createElement("span");
    newSpanElement.className = "position";
    if(charPosition == NUM_CHARS){
        btnSubmit.disabled = false;
    }
    var newTextElement = document.createTextNode(charPosition++);
    newSpanElement.appendChild(newTextElement);
    reorderElement.insertBefore(newSpanElement, charElement);
}

function startOver() {
    reorderElement.parentNode.style.display = "none";
    btnSubmit.disabled = true;
    accountOrder.value = accountId;
    var reoderElement = $("new-order");
    var charContainer = $("char-container");
    while (reoderElement.hasChildNodes()) {
        var firstChild = reoderElement.firstChild;
        if (firstChild.nodeName.toLowerCase() == "div") {
            if(firstChild.className == "trans char-info"){
                charContainer.appendChild(firstChild);
            }
        } else {
            reoderElement.removeChild(firstChild);
        }
    }
    addOnClickHandlers();
    charPosition = 1;
}