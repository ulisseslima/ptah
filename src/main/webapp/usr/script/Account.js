requestLocalizedMessages("title.editImage;profile.image.UnsupportedExtension");
objUserImage = $("user-image");
objUserImageElement = $("user-image-element");

var LEGAL_EXTENSIONS = [".jpg", ".png", ".gif", ".svg"];
var captured = null;
var imageWindow = null;
var objImagePreview;

function ImagePreview(imageWindow){
    createRequest("./Update?get=imageList", "getImageList");
    var picPreview = document.createElement("img");
    picPreview.id = "image-preview";
    picPreview.src = objUserImage.src;
    picPreview.alt = "...";    

    var divPreview = document.createElement("div");
    divPreview.className = "new-picture";
    divPreview.appendChild(picPreview);

    var btnPrevious = document.createElement("div");
    btnPrevious.className = "navigator";
    btnPrevious.innerHTML = "<h4 onclick='getPicture(\"previous\")'>&lt;</h4>";

    var btnNext = document.createElement("div");
    btnNext.className = "navigator";
    btnNext.innerHTML = "<h4 onclick='getPicture(\"next\")'>&gt;</h4>";

    imageWindow.appendChild(btnPrevious);
    imageWindow.appendChild(divPreview);
    imageWindow.appendChild(btnNext);
    return this;
}
function ImageWindow(){
    var self = this;
    var imageWindow = getBody("image-window", "editImage", self);
    new ImagePreview(imageWindow);
    this.imageWindow = imageWindow;

    var txtImageURL = document.createElement("input");
    txtImageURL.id = "txtImageURL";
    txtImageURL.title = "URL";
    txtImageURL.style.width = "325px";
    imageWindow.appendChild(txtImageURL);

    var btnPreview = document.createElement("button");
    btnPreview.innerHTML = "Preview";
    btnPreview.type = "button";
    btnPreview.addEventListener("click", function(){return self.preview()}, false);
    imageWindow.appendChild(btnPreview);

    var btnOK = document.createElement("button");
    btnOK.innerHTML = "OK";
    btnOK.type = "button";
    btnOK.addEventListener("click", function(){return self.edit()}, false);
    imageWindow.appendChild(btnOK);

    objUserImageElement.appendChild(imageWindow);
    txtImageURL.focus();
    return this;
}

ImageWindow.prototype = {
    get id(){
        if(!("_id" in this)) this._id = 0;
        return this._id;
    },
    set id(x){
        this._id = x;
    },
    get left(){
        return this.imageWindow.style.left;
    },
    set left(x){
        this.imageWindow.style.left = x;
    },
    get top(){
        return this.imageWindow.style.top;
    },
    set top(x){
        this.imageWindow.style.top = x;
    },
    get zIndex(){
        return this.imageWindow.style.zIndex;
    },
    set zIndex(x){
        this.imageWindow.style.zIndex = x;
    },
    preview: function(){        
        var txtImageURL = $("txtImageURL");
        loadImage(objImagePreview, txtImageURL.value);
        txtImageURL.select();
    },
    edit: function(){
        for(var i in LEGAL_EXTENSIONS){
            if(objImagePreview.src.lastIndexOf(LEGAL_EXTENSIONS[i]) != -1){
                var url = "./Update?get=newImage&imageUrl=" + objImagePreview.src;
                imageWindow.close();
                createRequest(url, "updatePage");
                break;
            }else{
                alert(getMessage("UnsupportedExtension"));
            }
        }
    },
    close: function(){
        var self = this;
        setTimeout(function(){objUserImageElement.removeChild(self.imageWindow)}, closeEffect(this.imageWindow));
    },
    onMouseDown: function(e){
        captured = this;
        this.startX = e.clientX - this.imageWindow.offsetLeft;
        this.startY = e.clientY - this.imageWindow.offsetTop;
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
}

function show_imageWindow(){
    if($("image-window")){
        $("txtImageURL").focus();
    }else{
        imageWindow = new ImageWindow();
        imageWindow.id = "image-window";        
        imageWindow.left = "-1px";
        imageWindow.top = "-1px";
        imageWindow.zIndex = highestZ++;
        objImagePreview = $("image-preview");
    }
}

var imageSet;
/**
 * Callback for an image list request.
 */
function getImageList(){
    if(responseIsReady()){
        imageSet = getResponseAsList();
    }
}

/**
 * Callback function for self.edit()
 */
function updatePage(){
    if(responseIsReady()){
        loadImage(objUserImage, getRawResponse());
    }
}

function loadImage(imgObject, imgSrc){
    imgObject.src = "usr/style/loading1.gif";
    imgObject.src = imgSrc;
}

var currentImage = 0;
/**
 * Gets the next or previous image in an image set.
 * @param mode Can be "next" or "previous".
 */
function getPicture(mode){
    var lastImage = imageSet.length - 1;
    if(mode == "next"){
        currentImage++;
        if(currentImage > lastImage)currentImage = 0;
        loadImage(objImagePreview, imageSet[currentImage]);
    }else{
        currentImage--;
        if(currentImage < 0)currentImage = lastImage;
        loadImage(objImagePreview, imageSet[currentImage]);
    }
}