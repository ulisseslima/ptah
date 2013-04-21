<%--Created on : 05/01/2010, 03:15:51
    Author     : Wonka--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="This tags creates a subtitle element that can receive a key for internationalized content and have a custom body" pageEncoding="UTF-8"%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="key" required="false" rtexprvalue="true" description="A key to load a localized message."%>
<%@attribute name="override" required="false" rtexprvalue="true" type="java.lang.Boolean"
             description="Determines if the default header size (h2) should be overriden"%>
<%@attribute name="type" required="false" rtexprvalue="true" 
             description="Used in conjunction with 'override', determines the type of header that should be used." %>

<%-- any content can be specified here e.g.: --%>
<${override? type : 'h2'}>
    <c:if test="${not empty key}">
        <fmt:message key="${key}"/>
    </c:if>
    <jsp:doBody/>
</${override? type : 'h2'}>