<%--Created on : 16/10/2009, 18:54:12
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="error.404.title" description="Resource not found in ${initParam['ServerName']}.">
    <div id="main">
        <dvl:body subtitle="HTTP 404">
            <fmt:message key="error.404.message"/>
        </dvl:body>
    </div>
</dvl:page>