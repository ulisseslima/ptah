/**
 * This file creates free instances of windows and provides methods for any other similar implementation.
 */

var captured = null;
var highestId = 0;

function Window(){
    var self = this;

    var promptWindow = document.createElement("div");
    promptWindow.className = "prompt-window";
    promptWindow.addEventListener("mousedown", function(e){return self.onMouseDown(e)}, false);
    promptWindow.addEventListener("click", function(){return self.onWindowClick()}, false);
    this.promptWindow = promptWindow;

    var closeButton = document.createElement("div");
    closeButton.className = "close-button";
    closeButton.addEventListener("click", function(event){return self.close(event)}, false);
    promptWindow.appendChild(closeButton);

    var editableArea = document.createElement("div");
    editableArea.className = "editable-area";
    editableArea.setAttribute("contenteditable", true);
    editableArea.addEventListener("keyup", function(){return self.onKeyUp()}, false);
    promptWindow.appendChild(editableArea);
    this.editField = editableArea;

    document.body.appendChild(promptWindow);
    return this;
}

function getBody(id, title, self){
    var promptWindow = document.createElement("div");
    promptWindow.id = id;
    promptWindow.className = "prompt-window";    
    promptWindow.addEventListener("mousedown", function(e){return self.onMouseDown(e)}, false);
    promptWindow.appendChild(getCloseButton(function(){return self.close()}));
    var titleElement = document.createElement("h2");
    titleElement.innerHTML = getMessage(title);
    promptWindow.appendChild(titleElement);
    return promptWindow;
}
function getCloseButton(onclickFunction){
    var closeButton = document.createElement("div");
    closeButton.className = "close-button";
    closeButton.addEventListener("click", onclickFunction, false);
    return closeButton;
}

function closeEffect(windowObject){
    var promptWindow = windowObject;

    var duration = .25; //duration of the close effect, in seconds
    promptWindow.style.webkitTransition = "-webkit-transform " + duration + "s ease-in, opacity " + duration + "s ease-in";    
    promptWindow.style.webkitTransformOrigin = "0 0";
    promptWindow.style.webkitTransform = "skew(30deg, 0deg) scale(0)";
    promptWindow.style.opacity = "0";
    return duration * 1000;
}

Window.prototype = {
    get id(){
        if(!("_id" in this)) this._id = 0;
        return this._id;
    },
    set id(x){
        this._id = x;
    },

    get text(){
        return this.editField.innerHTML;
    },
    set text(x){
        this.editField.innerHTML = x;
    },

    get left(){
        return this.promptWindow.style.left;
    },
    set left(x){
        this.promptWindow.style.left = x;
    },

    get top(){
        return this.promptWindow.style.top;
    },
    set top(x){
        this.promptWindow.style.top = x;
    },

    get zIndex(){
        return this.promptWindow.style.zIndex;
    },
    set zIndex(x){
        this.promptWindow.style.zIndex = x;
    },

    close: function(event){
        var promptWindow = this;

        var duration = .25; //duration of the close effect, in seconds
        this.promptWindow.style.webkitTransition = "-webkit-transform " + duration + "s ease-in, opacity " + duration + "s ease-in";        
        this.promptWindow.style.webkitTransformOrigin = "0 0";
        this.promptWindow.style.webkitTransform = "skew(30deg, 0deg) scale(0)";
        this.promptWindow.style.opacity = "0";

        var self = this;
        setTimeout(function(){document.body.removeChild(self.promptWindow)}, duration * 1000);
    },

    onMouseDown: function(e){
        captured = this;
        this.startX = e.clientX - this.promptWindow.offsetLeft;
        this.startY = e.clientY - this.promptWindow.offsetTop;
        this.zIndex = highestZ++;

        var self = this;
        if(!("mouseMoveHandler" in this)){
            this.mouseMoveHandler = function(e){
                return self.onMouseMove(e);
            }
            this.mouseUpHandler = function(e){
                return self.onMouseUp(e);
            }
        }
        document.addEventListener("mousemove", this.mouseMoveHandler, true);
        document.addEventListener("mouseup", this.mouseUpHandler, true);
        return false;
    },

    onMouseMove: function(e){
        if(this != captured) return true;

        this.left = e.clientX - this.startX + "px";
        this.top = e.clientY - this.startY + "px";
        return false;
    },

    onMouseUp: function(e){
        document.removeEventListener("mousemove", this.mouseMoveHandler, true);
        document.removeEventListener("mouseup", this.mouseUpHandler, true);
        return false;
    },

    onWindowClick: function(e){
        this.editField.focus();
        getSelection().collapseToEnd();
    },
}

function newWindow(){
    var promptWindow = new Window();
    promptWindow.id = highestId++;
    promptWindow.left = Math.round(Math.random() * 400) + "px";
    promptWindow.top = Math.round(Math.random() * 500) + "px";
    promptWindow.zIndex = highestZ++;
}

/**
 * Changes the display style of an element to "none", turning the element invisible.
 * @param element The element to be hidden.
 */
function close(element){
    element.style.display = "none";
}