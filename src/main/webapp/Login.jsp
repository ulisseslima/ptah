<%--Created on : Jan 8, 2010, 4:58:48 PM
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>

<dvl:page titleKey="login.title" ajax="true">
    <div id="main">
        <dvl:body key="login.subtitle">
            <form action="./Authenticate" onsubmit="return checkForm('btnLogin')" method="post">
                <h3>Last location: ${lastLocation}</h3>
                <dvl:textInput id="txtName" label="signup.name" maxLength="48" style="required-field"/>
                <dvl:textInput id="txtPassword" label="user.password" maxLength="64" password="true" style="required-field"/>

                <dvl:exception test="${allFieldsRequired}" type="fail" i18nMessage="forms.exception.allFieldsRequired"/>
                <dvl:exception test="${loginFail}" type="fail" i18nMessage="login.invalid"/>

                <dvl:submit action="Login" label="login.title"/>
            </form>
        </dvl:body>
    </div>
</dvl:page>