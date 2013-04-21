<%--Created on : 16/10/2009, 17:49:06
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<dvl:page titleKey="error.title">
    <div id="main">
        <dvl:body key="error.header">
            <form id="frmMain" name="frmMain" action="">
                <div id="subPage">
                    <c:if test="${pageContext.exception != null}">
                        <div id="exception" class="sub-page">
                            <fmt:message key="error.reason.a"/>
                            <b>(${pageContext.exception})</b>
                            <fmt:message key="error.reason.b"/>
                        </div>
                    </c:if>
                    <p>
                        <fmt:message key="error.trythis"/>
                    </p>
                    <p>
                        <fmt:message key="error.tryagain"/>
                    </p>
                    <p>
                        <fmt:message key="error.report"/> ${initParam["ContactMail"]}
                    </p>
                </div>
            </form>
        </dvl:body>
    </div>
</dvl:page>
