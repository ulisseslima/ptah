/**
 * @return the mail content element.
 */
function $mail(id){
    return $("mail-content-"+id);
}
/**
 * @return the mail text message element.
 */
function $content(id){
    return $("mail-content-text-"+id);
}

/**
 * Loads a mail message from the server.
 */
function getMail(id){
    if($content(id).innerHTML == "<hr>"){
        createRequest(
            "./Mail?action=getMail&id=" + id,
            "showMail");
    }else{
        //noneedtorequest
    }
}
/**
 * Callback for getMail()
 */
function showMail(){
    if(responseIsReady()){
        var content = $content(getResponseId());
        var mail = $mail(getResponseId());
        switch(responseCode()){
            case SUCCESS:
                content.innerHTML = "<p>"+getRichResponse()+"</p>";
                break;
            case FAIL:
                appendReplaceableMessage(getResponseMessage(), "fail", content, "no-mail");
                break;
        }
        mail.style.display = "block";
    }
}
/**
 * Attaches an item to the message.
 */
function attachItem(){}