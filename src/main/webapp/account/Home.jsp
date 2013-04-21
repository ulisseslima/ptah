<%--Created on : 18/04/2010, 00:55:21
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="user" tagdir="/WEB-INF/tags/user/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="profile.Title" styles="accounts.css,requests.css" ajax="true"
          scripts="${hasRequests? 'Account.js,RequestElements.js,RequestManage.js' : 'Account.js'}">
    <div id="main">
        <dvl:body key="profile.Title">
            <dvl:section key="requests.Title" test="${hasRequests}">
                <div id="groups"></div>
                <c:forEach var="userRequest" items="${userRequests}">
                    <div class="round item" id="request-${userRequest.id}" onmouseover="showGroups(${userRequest.id})">
                        <dvl:button id="btnRequest${userRequest.id}" label="request.Accept" function="acceptRequest('${userRequest.id}')">
                            <dvl:or key="request.Ignore" url="javascript:ignoreRequest('${userRequest.id}')"/>
                        </dvl:button> |
                        <a class="gray" href="./Profile?id=${userRequest.from.nameURL}">${userRequest.from.name}</a> <fmt:message key="request.description.Friendship"/>
                        <c:if test="${not empty userRequest.message}"><span class="request-message">"${userRequest.message}"</span></c:if>
                    </div>
                </c:forEach>
            </dvl:section>

            <dvl:section key="profile.LastLogin" test="${lastLogin != null}">
                ${lastLogin.lastLogin}
            </dvl:section>
        </dvl:body>

        <dvl:news/>
    </div>
    <aside>
        <dvl:aside title="${userSession.name}" center="true" popable="true">
            <div id="user-image-element" class="picture">
                ${userSession.editableImageElement}
            </div>
        </dvl:aside>

        <user:friends/>
    </aside>
</dvl:page>