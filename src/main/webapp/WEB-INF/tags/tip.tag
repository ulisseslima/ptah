<%--Created on : 16/02/2010, 01:11:41
    Author     : Wonka--%>
<%@tag description="This tag creates e tip element." pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="titleKey" required="true" rtexprvalue="true" description="The tip description."%>
<%@attribute name="function" required="true" rtexprvalue="true" description="This tip's onclick function." %>

<span class="action-tip" onclick="${function}" title=${"\""}<fmt:message key="${titleKey}"/>${"\""}></span>