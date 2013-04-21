<%--Created on : Dec 31, 2009, 9:26:31 PM
    Author     : Wonka--%>
<%@tag description="This tag generates a button." pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="action" required="true" rtexprvalue="true"
             description="The type of button."%>
<%@attribute name="label" required="true" rtexprvalue="true"
             description="The label key of the button."%>
<%@attribute name="function" required="false" rtexprvalue="true"
             description="A Javascript function to be called."%>
<%@attribute name="startDisabled" required="false" rtexprvalue="true"
             description="Usage: a value of 'disabled' to start disabled"%>

<%-- content --%>
<button type="submit" id="btn${action}" name="action" value="${action}" onclick="${function}" ${startDisabled}>
    <fmt:message key="${label}"/>
</button><jsp:doBody/>