<%--Created on : 01/04/2010, 10:42:19
    Author     : Wonka--%>
<%@tag description="This tag creates an equip location element containing the equip location image and title." pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="location" required="true" rtexprvalue="true" description="The equip location i18n String. This String will be used to load both the equip title and image."%>

<img src="usr/etc/${location}.png" alt="${location}" title=${"'"}<fmt:message key="${location}"/>${"'"}/>