<%--Created on : 23/01/2010, 23:09:00
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="char" tagdir="/WEB-INF/tags/char/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="accounts.adminAccount" styles="accounts.css,storage.css" scripts="AccountManage.js,Storage.js" ajax="true">
    <%--<c:if test="${account.level == 99}">
        <aside>
            <dvl:aside title="Admin">
                Congrats! You're an admin, go nuts!
            </dvl:aside>
        </aside>
    </c:if>--%>
    <div id="main">
        <dvl:body key="account.storage.action.Box" style="closable " id="action-box-parent">
            <div id="action-box"><div class="close-button" onclick="cancelAction()"></div></div>
            <hr>
            <dvl:submit action="Sell" label="account.storage.action.Sell"/>
            <dvl:submit action="Mail" label="account.storage.action.Mail"/>
        </dvl:body>
        <dvl:body key="accounts.adminAccount">
            <char:account chars="${chars}"/>

            <form action="./AccountAction" method="post" onsubmit="checkForm('btnReorder')">
                <input type="hidden" id="NUM_CHARS" name="NUM_CHARS" value="${account.numChars}"/>
                <input id="account-order" name="account-order" type="hidden" value="${account.accountId}">
                <div id="new-order-element" style="display: none">
                    <hr>
                    <h4><fmt:message key="account.newOrder"/></h4>
                    <div class="section" id="new-order"></div>
                    <dvl:button id="btnStartOver" label="account.manage.startOver" function="startOver()"/>
                    <dvl:submit action="Reorder" label="accounts.reorderAccount" startDisabled="disabled"/>
                </div>
            </form>

            <h3 class="round tip"><fmt:message key="hint.account.Reorder"/></h3>
            <dvl:exception test="${warnCharIsOnline}" type="warning" i18nMessage="account.warn.hasOnlineChars"/>
            <dvl:exception test="${charIsOnline}" type="fail" i18nMessage="account.hasOnlineChars"/>
            <dvl:exception test="${reorderException}" type="fail" i18nMessage="exception.OperationFailed"/>
            <dvl:exception test="${notImplementedYet}" type="fail" i18nMessage="exception.NotImplementedYet"/>
        </dvl:body>
    </div>

    <dvl:list items="${storage}" key="account.Storage" onclickHandlers="true"/>
</dvl:page>