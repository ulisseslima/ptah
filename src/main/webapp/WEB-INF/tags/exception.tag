<%--Created on : 14/07/2009, 09:51:07
    Author     : Wonka--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@tag description="Sends a message if an exception occurred [test evals to true]."
       pageEncoding="UTF-8"%>

<%@attribute name="id" required="false" rtexprvalue="true" description="An optional element id." %>
<%@attribute name="test" required="true" rtexprvalue="true" type="java.lang.Boolean"
             description="Boolean indicating whether the exception attribute is true"%>
<%@attribute name="type" required="true" rtexprvalue="true"
             description="Style for the message (success, warning or fail)."%>
<%@attribute name="i18nMessage" required="true" rtexprvalue="true"
             description="Message key to be shown using i18n resources."%>

<c:if test="${test}">    
    <h3 <c:if test="${not empty id}">id="${id}"</c:if>class="${type}">
        <fmt:message key="${i18nMessage}"/> <jsp:doBody/>
    </h3>
</c:if>