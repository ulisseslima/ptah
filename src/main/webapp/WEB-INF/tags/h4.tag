<%--Created on : 13/04/2010, 01:41:34
    Author     : Wonka--%>
<%@tag description="Creates an h4 element with an internationalized content." pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="key" required="true" rtexprvalue="true" description="A key to load a localized message."%>

<h4><fmt:message key="${key}"/><jsp:doBody/></h4>