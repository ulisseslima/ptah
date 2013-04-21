<%--Created on : Jan 7, 2010, 9:21:22 PM
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="char" tagdir="/WEB-INF/tags/char/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="accounts.title" styles="accounts.css,requests.css" ajax="true"
          scripts="${hasRequests? 'RequestElements.js,RequestManage.js' : null}">
    <div id="main">
        <dvl:body key="accounts.title">
            <dvl:exception test="${charLimitReached}" type="fail" i18nMessage="account.exception.CharLimitReached"/>

            <dvl:section key="requests.Title" test="${hasRequests}">                
                <c:forEach var="charRequest" items="${charRequests}">
                    ${charRequest.from.name} wants to be added to your friends list.<br>
                </c:forEach>
            </dvl:section>

            <div class="account">
                <h4><a href="./NewAccount"><fmt:message key="accounts.createNew"/></a></h4>
            </div>
            <c:if test="${accounts != null}">
                <c:forEach var="account" items="${accounts}">
                    <char:account chars="${requestScope[account.accountId]}" account="${account}" allowRandomCreation="true"
                                  charPage="true"/>
                </c:forEach>
            </c:if>
            <div class="footer"></div>
        </dvl:body>
    </div>    
</dvl:page>