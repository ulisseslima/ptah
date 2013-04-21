<%--Created on : 05/04/2010, 20:38:18
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="user" tagdir="/WEB-INF/tags/user/" %>
<%@taglib prefix="char" tagdir="/WEB-INF/tags/char/"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<dvl:page titleKey="char.view" titleAid="- ${char.className} ${char.name} lv${char.baseLevel}/${char.jobLevel}" ajax="true"
          styles="accounts.css,char.css,requests.css" scripts="${userSession.valid? 'RequestElements.js,Requests.js' : null}">
    <div id="main">
        <dvl:body subtitle="${char.name}">
            <char:info>
                <dvl:button id="btnFriend" label="request.friendship" function="request('friend')"/><br>
                <%-- This will make an ajax call and return a list with all the user's characters. The user will be able to select all
                chars at once. Or, instead of having to add each char, add all the chars of a friend.--%>
                <dvl:button id="btnGuild" label="request.guild" function="request('guild')"/>
                <%-- isso deve aparecer só para os donos de guild? --%>
                <dvl:button id="btnParty" label="request.party" function="request('party')"/>
                <%-- This should be implement as an ajax request that will search for all the user's parties. The list will be returned
                as radio buttons elements that will be appended bellow the party button;
                each radio will have a url like "./CharAction?action=party&party=id"--%>
                <br>
            </char:info>
            <char:list of="${partyMembers}" leader="${partyLeaderId}" subtitle="Party: ${char.party.name}"/>
            <char:list of="${guildMembers}" leader="${guildLeaderId}" subtitle="Guild: ${char.guild.name}"/>
        </dvl:body>
    </div>
    <aside>
        <dvl:aside title="${char.user.name}" center="true">
            <dvl:profile servlet="View" imageElement="${char.user.imageElement}" userId="${char.user.id}" viewId="${char.nameURL}"/>
        </dvl:aside>

        <user:friends/>
    </aside>
</dvl:page>