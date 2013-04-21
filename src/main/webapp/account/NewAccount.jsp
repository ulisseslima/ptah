<%--Created on : 16/01/2010, 19:17:47
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="accounts.createNew" ajax="true" aidScript="Signup.js">
    <div id="main">
        <dvl:body key="accounts.createNew">
            <form action="./NewAccount" id="frmNewAccount" method="post" onsubmit="return checkForm('btnNewAccount')">
                <dvl:textInput id="txtName" label="signup.name" style="required-field" maxLength="23" function="onblur=\"checkAvailability('userId', 'txtName', './NewAccount')\""
                               hintKey="hint.signup.name"/>
                <dvl:textInput id="txtPassword1" label="user.password" style="required-field" maxLength="32" password="true"
                               hintKey="hint.signup.password"/>
                <dvl:textInput id="txtPassword2" label="user.passwordConfirmation" style="required-field" maxLength="32" password="true" function="onkeyup=\"checkMatch()\""/>
                <label><fmt:message key="user.gender"/>
                    <input type="radio" value="F" name="gender"><fmt:message key="user.gender.female"/>
                    <input type="radio" value="M" name="gender" checked><fmt:message key="user.gender.male"/>
                </label><br>

                <hr>
                <dvl:submit action="NewAccount" label="accounts.createNew"/> <dvl:or key="cancel" url="./Accounts"/>

                <dvl:exception test="${nameTooShort}" type="fail" i18nMessage="signup.exception.nameTooShort"/>
                <dvl:exception test="${nameExists}" type="fail" i18nMessage="signup.exception.nameExists"/>
                <dvl:exception test="${allFieldsRequired}" type="fail" i18nMessage="forms.exception.allFieldsRequired"/>
                <dvl:exception test="${weakPassword}" type="fail" i18nMessage="signup.exception.weakPassword"/>
                <dvl:exception test="${passwordsDontMatch}" type="fail" i18nMessage="signup.exception.passwordsDontMatch"/>
                <dvl:exception test="${accountLimitReached}" type="fail" i18nMessage="signup.exception.accountLimitReached"/>
            </form>
        </dvl:body>
    </div>
</dvl:page>