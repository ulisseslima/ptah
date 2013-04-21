var captured = null;
var promptWindow;

function LoginWindow(){
    var self = this;

    var promptWindow = document.createElement("div");
    promptWindow.id = "login-window";
    promptWindow.className = "prompt-window";
    promptWindow.addEventListener("mousedown", function(e){return self.onMouseDown(e)}, false);
    this.promptWindow = promptWindow;

    var title = document.createElement("h2");
    title.innerHTML = "Login";
    promptWindow.appendChild(title);

    var txtUser = document.createElement("input");
    txtUser.id = "txtUser";
    txtUser.name = "txtUser";
    txtUser.setAttribute("placeholder", "id");
    promptWindow.appendChild(txtUser);

    var txtPass = document.createElement("input");
    txtPass.type = "password";
    txtPass.id = "txtPass";
    txtPass.name = "txtPass";
    txtPass.setAttribute("placeholder", "password");
    promptWindow.appendChild(txtPass);

    var btnLogin = document.createElement("button");    
    btnLogin.innerHTML = "Login";
    btnLogin.type = "button";
    btnLogin.name = "action";
    btnLogin.value = "login";
    btnLogin.addEventListener("click", function(){return self.login()}, false);
    promptWindow.appendChild(btnLogin);

    var closeButton = document.createElement("div");
    closeButton.className = "close-button";
    closeButton.addEventListener("click", function(){return self.close()}, false);
    promptWindow.appendChild(closeButton);
    
    document.body.appendChild(promptWindow);
    txtUser.focus();
    return this;
}

LoginWindow.prototype = {
    get id(){
        if(!("_id" in this)) this._id = 0;
        return this._id;
    },
    set id(x){
        this._id = x;
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

    login: function(){
        var url = "./Login?user=";
        if(self.txtUser){
            url += self.txtUser.value + "&id=" +  self.txtPass.value;
        }else{
            url += $("txtUser").value + "&id=" + $("txtPass").value;
        }
        createRequest(url, "loginResponse");
        setLoadingStatus(self.txtPass);        
    },

    close: function(){        
        var self = this;
        setTimeout(function(){document.body.removeChild(self.promptWindow)}, closeEffect(this.promptWindow));
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
}

function show_loginWindow(){
    if($("login-window")){
        $("txtUser").focus();
    }else{
        promptWindow = new LoginWindow();
        promptWindow.id = "login-window";        
        promptWindow.left = ((screen.width / 2) - 200) + "px";
        promptWindow.top = ((screen.height / 2) - 200) + "px";        
        promptWindow.zIndex = highestZ++; 
    }
}