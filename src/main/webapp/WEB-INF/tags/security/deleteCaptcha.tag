<%--Created on : 03/01/2010, 12:23:55
    Author     : Wonka--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@tag description="This captcha fills a text field with some code that must be deleted before submitting the form." pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<br><dvl:textInput id="txtCaptcha" label="captcha.DeleteCaptcha" maxLength="128" value="${captchaQuestion}"/>