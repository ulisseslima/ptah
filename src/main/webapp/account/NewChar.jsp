<%--Created on : 16/01/2010, 19:17:57
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="security" tagdir="/WEB-INF/tags/security/" %>

<dvl:page titleKey="accounts.createNewChar" ajax="true" scripts="Signup.js,Char.js" style="char.css">
    <div id="main">
        <dvl:body key="accounts.createNewChar">
            <dvl:exception test="${newAccount}" type="success" i18nMessage="accounts.createNew.Success"/>
            <dvl:exception test="${invalidCaptcha}" type="fail" i18nMessage="captcha.invalidCaptcha"/>
            <dvl:exception test="${nameTooShort}" type="fail" i18nMessage="signup.exception.nameTooShort"/>
            <dvl:exception test="${nameExists}" type="fail" i18nMessage="signup.exception.nameExists"/>
            <dvl:exception test="${allFieldsRequired}" type="fail" i18nMessage="forms.exception.allFieldsRequired"/>
            <dvl:exception test="${invalidAttributes}" type="fail" i18nMessage="forms.exception.allFieldsRequired"/>
            <dvl:exception test="${charLimitReached}" type="fail" i18nMessage="account.exception.CharLimitReached"/>

            <form action="./NewChar" method="post" onsubmit="return checkForm('btnNewChar')">
                <input type="hidden" name="account" value="${param['account']}">
                <dvl:textInput id="txtName" label="signup.name" style="required-field" maxLength="48" function="onblur=\"checkAvailability('name', 'txtName', './NewChar')\""
                               hintKey="hint.signup.name">
                    <dvl:tip function="requestRandomName('txtName')" titleKey="char.randomName"/>
                </dvl:textInput>
                <div id="status"><input type="hidden" name="stats" id="stats" value="5,5,5,5,5,5">
                    <div>
                        <button class="stat-button" id="str" type="button" onclick="raise(0, -6)">str 5</button>
                        <div class="status-bar">
                            <div id="str-int" class="status-meter"></div>
                        </div>
                        <button class="stat-button" id="int" type="button" onclick="raise(0, 6)">int 5</button>
                    </div>
                    <div>
                        <button class="stat-button" id="vit" type="button" onclick="raise(1, -6)">vit 5</button>
                        <div class="status-bar">
                            <div id="vit-dex" class="status-meter"></div>
                        </div>
                        <button class="stat-button" id="dex" type="button" onclick="raise(1, 6)">dex 5</button>
                    </div>
                    <div>
                        <button class="stat-button" id="agi" type="button" onclick="raise(2, -6)">agi 5</button>
                        <div class="status-bar">
                            <div id="agi-luk" class="status-meter"></div>
                        </div>
                        <button class="stat-button" id="luk" type="button" onclick="raise(2, 6)">luk 5</button>
                    </div>
                </div>

                <security:deleteCaptcha/>                
                <hr>
                <dvl:submit action="NewChar" label="accounts.createNewChar">
                    <dvl:or key="cancel" url="./Accounts"/>
                </dvl:submit>
            </form>            
        </dvl:body>
    </div>
</dvl:page>