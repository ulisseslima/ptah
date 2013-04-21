var groupContainer = document.createElement("div");
var groupNames = "";
function requestGroups(){
    createRequest("./Update?get=groups", "getGroups");
}
function newCheckBox(name){
    if(groupNames.indexOf(name) < 0){
        var chkBox = document.createElement("input");
        chkBox.type = "checkbox";
        chkBox.name = "groups";
        chkBox.value = name;
        groupNames += ";".concat(name);

        var checkLabel = newLabel();
        checkLabel.appendChild(chkBox);
        checkLabel.innerHTML += name;
        groupContainer.insertBefore(checkLabel, $("new-group"));
    }
}

function newGroup(){
    var newGroupName = document.createElement("input");
    newGroupName.id = "new-group";
    newGroupName.type = "text";
    newGroupName.maxLength = "20";
    newGroupName.className = "newGroup";
    newGroupName.placeholder = getMessage("NewGroup");
    newGroupName.onfocus = function(){
        newGroupName.select();
    };
    
    newGroupName.onkeydown = function(event){
        if(key_pressed(RETURN_KEY, event)){
            if(newGroupName.value.trim().length > 1){
                newCheckBox(fix(newGroupName.value));
                newGroupName.value = "";
            }
        }
    }
    return newGroupName;
}
function getSelectedGroups(){
    var groupsCSV = [];
    var selectedGroups = $("groups").getElementsByTagName("label");
    if(selectedGroups != null){
        for(var i in selectedGroups){
            var chkBox = selectedGroups[i].firstChild;
            if(chkBox && chkBox.checked){
                if(chkBox.value.length > 1){
                    groupsCSV.push(chkBox.value);
                }
            }
        }
    }
    if(groupsCSV[0] == null) groupsCSV = ["Friends"];
    return groupsCSV.join(";");
}

function newLabel(text){
    var label = document.createElement("label");
    label.className = "group";
    if(text != null){
        label.innerHTML += text;
    }
    return label;
}

function newSpan(label){
    var span = document.createElement("span");
    span.style.display = "block";
    span.innerHTML = label;
    return span;
}
/**
 * Creates the Group checkboxes.
 */
function setGroups(){
    var groupsTitle = newLabel(getMessage("Title"));
    groupsTitle.className = "groups-title";
    groupContainer.appendChild(groupsTitle);
    var groups = getResponseAsCSV();
    for(var i in groups){
        newCheckBox(groups[i]);
    }
}