<%--Created on : 03/06/2010, 04:00:34
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<dvl:page titleKey="signup.facebook" ajax="true">
    <div id="main">
        <dvl:body key="signup.facebook.info">
            <form action="./Facebook">
                <dvl:textInput id="txtName" label="signup.name" maxLength="48" style="required-field"/>
                <dvl:textInput id="txtPassword" label="user.password" maxLength="64" password="true" style="required-field"/>
                <dvl:textInput id="txtPassword2" label="user.passwordConfirmation" maxLength="64" password="true" style="required-field"/>
                <%--Usar as funções de procurar nome da outra página de Signup
                use Permission.authorizationUrl(apiKey, Permission.EMAIL);--%>
                <dvl:submit label="signup.express.submit" action="newAccount"/>
            </form>
        </dvl:body>
    </div>
</dvl:page>