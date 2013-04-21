<%--Created on : 15/04/2010, 15:00:06
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="user" tagdir="/WEB-INF/tags/user/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<dvl:page titleKey="profile.Title" titleAid="- ${user.name}" ajax="true"
          styles="accounts.css,requests.css" scripts="${userSession.valid? 'RequestElements.js,Requests.js' : null}">
    <div id="main">
        <dvl:body key="profile.Activities">
            <dvl:section key="profile.LastLogin" test="${lastLogin != null}">
                ${lastLogin.lastLogin}
            </dvl:section>
        </dvl:body>
    </div>
    <aside>
        <dvl:aside title="${user.name}" center="true">
            <dvl:profile servlet="Profile" imageElement="${user.imageElement}" userId="${user.id}" viewId="${user.nameURL}"
                         userName="${user.name}"/>
        </dvl:aside>
        
        <user:friends/>
    </aside>
</dvl:page>