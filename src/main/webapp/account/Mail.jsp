<%--Created on : Jun 6, 2010, 4:06:30 PM
    Author     : wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<dvl:page titleKey="mail.inbox" ajax="true" scripts="Mail.js" styles="accounts.css,storage.css,mail.css">
    <div id="main">
        <dvl:body key="mail.send" formAction="Mail">
            <dvl:exception test="${messageSent}" type="success" i18nMessage="mail.sent"/>
            <dvl:exception test="${notAllMessagesSent}" type="warning" i18nMessage="mail.partiallySent"/>
            <dvl:exception test="${messageNotSent}" type="fail" i18nMessage="mail.notSent"/>

            <dvl:select id="selectSender" items="${availableChars}" check="${availableChars[0].id}"
                        label="mail.sender"/>
            <dvl:textInput id="txtRecipients" label="mail.recipient" maxLength="99" size="69"
                           required="true"/>
            <dvl:textInput id="txtSubject" label="mail.subject" maxLength="99" size="69"/>
            <dvl:textInput id="txtZeny" label="mail.zeny" maxLength="10"/>
            <dvl:textArea id="txtContent" cols="72" maxLength="255" required="true"/>

            <dvl:button id="btnAttachItem" label="mail.attach" function="attachItem()"/>
            <dvl:submit action="SendMail" label="mail.send"/>
        </dvl:body>

        <dvl:body key="mail.inbox">
            <c:if test="${inbox != null}">
                <c:forEach var="message" items="${inbox}">
                    <div class="round item">
                        <div class="inline mail-headers">
                            <img src="${message.read? 'http://dl.dropbox.com/u/7798003/icons/indicator-messages.svg':'http://dl.dropbox.com/u/7798003/icons/mail-message-new.png'}" alt="${message.read}"/>
                            <dvl:boxy label="mail.zeny" test="${message.zeny > 0}">
                                ${message.zeny}
                            </dvl:boxy>
                            <dvl:boxy label="item" test="${message.itemId > 0}">
                                ${message.itemId} (${message.itemAmount})
                            </dvl:boxy>
                        </div>
                        <div class="inline mail">                            
                            <a class="mail-sender" target="_new" href="./View?id=${message.senderUrl}">${message.senderName}</a>
                            <a class="mail-sender">to</a>
                            <a class="mail-sender" target="_new" href="./View?id=${message.recipientUrl}">${message.recipientName}</a>                           
                            
                            <div class="mail-subject-${message.read}" onclick="getMail('${message.id}')">${message.shortSubject}</div>
                        </div>
                        <div class="inline mail-date">
                            ${message.dateSent}
                        </div>
                        <div id="mail-content-${message.id}" class="mail-content">
                            <div id="mail-content-text-${message.id}"><hr></div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </dvl:body>
    </div>
    <%-- Maybe a mail action history element here? --%>
</dvl:page>