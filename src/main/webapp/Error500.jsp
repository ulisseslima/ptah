<%--Created on : 26/09/2010, 15:33:20
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="error.500.title">
    <div id="main">
        <dvl:body subtitle="HTTP 500">
            <fmt:message key="error.500.message"/>
        </dvl:body>
    </div>
</dvl:page>