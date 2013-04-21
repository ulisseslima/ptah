<%--Created on : 17/05/2010, 20:07:01
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<dvl:page preTitle="${initParam['ServerName']}" titleKey="on" titleAid=" Facebook" aidScript="Facebook.js" style="facebook.css" ajax="true"
          description="This page serve as an entry point for Apis from Facebook.">
    <div id="main">
        <dvl:body key="facebook.bind">
            <h3><b><fmt:message key="facebook.welcome"/>, ${client.name}!</b></h3>
            <h3><fmt:message key="facebook.info"/></h3>
            <div>
                <div class="block">
                    <dvl:textInput id="txtName" label="signup.name" maxLength="48" style="required-field"/>
                    <dvl:textInput id="txtPassword" label="user.password" maxLength="64" password="true" style="required-field"/>

                    <input type="hidden" id="facebookId" value="${client.id}">
                    <dvl:button function="bindAccount()" id="btnBind" label="facebook.bind"/>
                </div>
                <div class="block">
                    <img src="${client.image}" alt="user image" title="${client.name}"/>
                </div>
            </div>
        </dvl:body>

        <c:if test="${friends != null}">
            <h3>Your friends:</h3>
            <c:forEach var="contact" items="${contacts}">
                <div style="display: inline-block" title="${contact.name} (${contact.mail})">
                    <img src="${contact.image}" alt="user image"/>
                </div>
            </c:forEach>
        </c:if>
    </div>
</dvl:page>