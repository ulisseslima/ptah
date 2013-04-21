/**
 * Binds a Facebook account to an ptah account.
 */
function bindAccount(){
    var txtPass = $("txtPassword");
    setLoadingStatus(txtPass);
    var name = $("txtName").value;
    var pass = txtPass.value;
    var fbId = $("facebookId").value;
    var url = "./Facebook?action=bindAccount"
        +"&name=" + name
        +"&pass=" + pass
        +"&fbId=" + fbId;
    createRequest(
        url,
        "response_bindAccount");
}

/**
 * Callback for bindAccount()
 */
function response_bindAccount(){
    if(responseIsReady()){
        if(responseCode() == "success"){
            appendReplaceableMessage(getResponseMessage(), responseCode(), "txtPassword", "login-check");
            document.location.href = "./Accounts";
        }else{
            $("txtPassword").value = "";
            var txtName = $("txtName");
            txtName.focus();
            txtName.select();
            appendReplaceableMessage(getResponseMessage(), responseCode(), "txtPassword", "login-check");
        }
    }
}
