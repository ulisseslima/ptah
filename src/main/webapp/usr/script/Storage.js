try{requestLocalizedMessages("account.storage.item.Amount");}catch(ajaxNotLoaded){/*alert("No ajax")*/}
if($("action-box")){
    var actionBox = $("action-box");
    actionBox.parentNode.style.display = "none";
}
var itemQuery = [];
var storage = $("storage");
var storageDB = storage.cloneNode(true);
var storageItems = storageDB.getElementsByTagName("div");

function filter(param){
    dropChildren(storage);
    if(param.length > 0){        
        for(var i in storageItems){
            if(storageItems[i] != null){
                if(storageItems[i].nodeName == "DIV"){
                    var itemName = storageItems[i].id.toLowerCase();
                    if(itemName.indexOf(param.toLowerCase()) > 0){
                        storage.appendChild(storageItems[i].cloneNode(true));
                    }
                }
            }
        }
    }else{
        restoreStorage();
    }
}

/**
 * Restore all the previously contained storage items.
 */
function restoreStorage(){
    for(var i in storageItems){
        if(storageItems[i].nodeName == "DIV"){
            storage.appendChild(storageItems[i].cloneNode(true));
        }
    }
}

/**
 * Response codes:
 * - success: the appraisal was successful;
 * - unlog: the account has online chars;
 * - nozeny: the user has less than 200 zeny;
 * - illegal: the user tried to reveal an item that doesn't belong to him.
 * @param item The Item element.
 */
function requestIdentification(item){
    item.parentNode.className += " busy2";
    item.onclick = null;
    var itemId = item.id.split("_")[0];
    createRequest("./Update?get=Appraisal&item=" + itemId, "identificationResponse");
    itemQuery.push(itemId + "_" + item.innerHTML);
}

/**
 * Callback for requestIdentification().
 * A special check must be made to see if the item is in the action-box or the storage element.
 */
function identificationResponse(){
    if(responseIsReady()){
        var currentItem = itemQuery.shift();
        var item;
        $(currentItem)? item = $(currentItem) : item = $(currentItem + ".box");
        if(responseCode() == ("success" || "identified")){
            item.className = "round storage-item";
        }else{
            alert(getResponseMessage());
            item.onclick = function(){
                requestIdentification(item)
            };
        }
    }
}

/**
 * Sends an Item to the Action Box.
 * @param element The Item element;
 * @param itemAmount The Item amount.
 */
function box(element, itemAmount){
    var selectedAmount = 1;
    if(itemAmount > 1){
        selectedAmount = parseInt(prompt(getMessage("Amount"), itemAmount));
        if(selectedAmount > itemAmount){
            selectedAmount = itemAmount;
        }else if(selectedAmount < 0){
            selectedAmount = parseInt(selectedAmount) + parseInt(itemAmount);
        }
    }
    if(selectedAmount > 0){
        actionBox.parentNode.style.display = "block";
        var elementInBox = $(element.id + ".box");
        if(elementInBox){
            var amountInBox = parseInt(elementInBox.getElementsByTagName("span")[0].innerHTML);
        }
        if(selectedAmount == itemAmount){
            convert(element);
            if(elementInBox){
                elementInBox.getElementsByTagName("span")[0].innerHTML = amountInBox + selectedAmount;
                storage.removeChild(element);
            }else{
                actionBox.insertBefore(element, actionBox.firstChild);
            }
        }else{
            element.getElementsByTagName("span")[0].innerHTML = itemAmount - selectedAmount;
            if(elementInBox){
                elementInBox.getElementsByTagName("span")[0].innerHTML = amountInBox + selectedAmount;
            }else{
                var elementPart = element.cloneNode(true);
                elementPart.getElementsByTagName("span")[0].innerHTML = selectedAmount;
                convert(elementPart);
                actionBox.insertBefore(elementPart, actionBox.firstChild);
            }
        }
    }
}

/**
 * Converts a storage element to an action box element.
 * @param item The item element.
 */
function convert(item){
    item.id += ".box";
    var img = item.getElementsByTagName("img")[0];
    img.src = img.src.replace("small", "large");
    var srcId = parseInt(img.alt);
    if(srcId > 4000 && srcId < 4700){
        img.src = img.src.substr(0, img.src.lastIndexOf("/")) + "/" + srcId + ".bmp";
    }
}

/**
 * Closes the Action Box and restores all the Storage items.
 */
function cancelAction(){
    dropChildren(actionBox);
    actionBox.appendChild(getCloseButton(cancelAction));
    dropChildren(storage);
    restoreStorage();
    close(actionBox.parentNode);
}