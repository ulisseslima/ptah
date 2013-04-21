<%--Created on : 14/01/2010, 02:40:36
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="online.players" aidScript="OnlinePlayers.js" ajax="true" style="accounts.css" description="Players currently online on the server.">
    <div id="main">
        <dvl:body key="online.players">
            <button type="button" id="btnCheck" onclick="requestOnlinePlayers()">Update</button>
            <div class="section" id="char-container">
                <c:if test="${onlinePlayers != null}">
                    <c:forEach items="${onlinePlayers}" var="char">
                        <div class="trans char-info">
                            <a class="black" href="./View?id=${char.nameURL}">${char.nameClassGuild}</a>
                            <span class="level">${char.baseLevel}/${char.jobLevel}</span><br>
                            <span class="icon">☼${char.lastMap}</span>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <dvl:exception test="${onlinePlayers == null}" i18nMessage="online.none" type="warning" id="playersCheck"/>
        </dvl:body>
    </div>
</dvl:page>