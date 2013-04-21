var elementId;
function requestRandomName(targetElementId){
    elementId = targetElementId;
    createRequest("./Get?a=randomName", "getRandomName");
}

function getRandomName(){
    if(responseIsReady()){
        $(elementId).value = getRawResponse();
    }
}

var attributeBars = [$("str-int"), $("vit-dex"), $("agi-luk")];
var sliderPosition = [25, 25, 25];
var attributeButtons = [$("str"), $("int"), $("vit"), $("dex"), $("agi"), $("luk")];
var stats = [5, 5, 5, 5, 5, 5];

function raise(index, val){
    if(val < 0){
        if(sliderPosition[index] > 5){
            attributeBars[index].style.left = (sliderPosition[index] += val) + "px";
        }
        count(index, true);
    }else{
        if(sliderPosition[index] < 45){
            attributeBars[index].style.left = (sliderPosition[index] += val) + "px";
        }
        count(index, false);
    }
}

function count(index, leftShift){
    var attributeButton = [attributeButtons[index + index], attributeButtons[index + index +1]];
    var counters = [];
    for(var i in attributeButton){
        counters[i] = [parseInt(attributeButton[i].innerHTML.split(" ")[1])];
    }
    if(leftShift){
        counters[0] > 8? counters[0] = 9 : counters[0]++;
        counters[1] < 2? counters[1] = 1 : counters[1]--;
    }else{
        counters[1] > 8? counters[1] = 9 : counters[1]++;
        counters[0] < 2? counters[0] = 1 : counters[0]--;
    }
    for(var j in attributeButton){
        attributeButton[j].innerHTML = attributeButton[j].innerHTML.split(" ")[0] + " " + counters[j];        
    }
    stats[index + index] = counters[0];
    stats[index + index +1] = counters[1];
    $("stats").value = stats.toString();
}