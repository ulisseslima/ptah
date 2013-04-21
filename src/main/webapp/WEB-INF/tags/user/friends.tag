<%--Created on : 22/04/2010, 19:07:57
    Author     : Wonka--%>
<%@tag description="Creates an element containing all the User's friends" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="message"%>

<dvl:aside titleKey="profile.Friends" center="true">
    <c:forEach var="friend" items="${friends}">
        <div class="avatar">
            <a href="./Profile?id=${friend.nameURL}">
                <img src="${friend.image}" alt="${friend.name}" title="${friend.name}"/>
            </a>
        </div>
    </c:forEach>
</dvl:aside>