<%--Created on : 16/02/2010, 17:06:51
    Author     : Wonka--%>
<%@tag description="This tag offers an optional action. This action is generally by the side of a submit button." pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="key" required="true" rtexprvalue="true" description="The label of the action."%>
<%@attribute name="url" required="true" rtexprvalue="true" description="The URL of the action." %>
<%-- any content can be specified here: --%>
<fmt:message key="or"/> <a class="or" href="${url}"><fmt:message key="${key}"/></a>