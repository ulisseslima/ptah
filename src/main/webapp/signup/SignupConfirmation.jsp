<%--Created on : Jan 1, 2010, 2:05:54 AM
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="signup.confirmation" ajax="true">
    <div id="main">
        <dvl:body key="signup.confirmation.sub">
            <c:choose>
                <c:when test="${newUser.name != null or confirmationMailSent}">
                    <p>
                        <fmt:message key="signup.confirmation.msg.a"/> ${newUser.mail} <fmt:message key="signup.confirmation.msg.b"/>
                    </p>
                </c:when>
                <c:otherwise>
                    <a href="./Signup">Signup</a>
                </c:otherwise>
            </c:choose>
        </dvl:body>
    </div>
</dvl:page>