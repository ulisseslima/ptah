<%--Created on : 23/01/2010, 23:08:47
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="char" tagdir="/WEB-INF/tags/char/"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<dvl:page titleKey="char.Admin" styles="accounts.css,storage.css,char.css" aidScript="Storage.js"
          titleAid="- ${char.className} ${char.name} lv${char.baseLevel}/${char.jobLevel}">
    <div id="main">
        <dvl:body subtitle="<a href='./View?id=${char.nameURL}'>${char.name}</a>">
            <dvl:h4 key="accounts.adminAccount">: <a href="./Account?id=${char.account.accountId}">${char.account.userId}</a></dvl:h4>
            <char:list of="${partyMembers}" leader="${partyLeaderId}" subtitle="Party: ${char.party.name}"/>
            <char:list of="${guildMembers}" leader="${guildLeaderId}" subtitle="Guild: ${char.guild.name}"/>
            <char:info isPrivate="true"/>
            <char:skills/>
            <char:hotkeys/>

            <%--mostrar:<br>
            -opção pra salvar e carregar esquemas de hotkeys e inventario;
            -se a pessoa é solteira<br>
            -maybe show only online guild members, but provide a link to a more detailed guild page, with information such as donated exp and such
            -posição no mapa<br>
            -link pro storage;<br>
            -se tiver carrinho, mostrar o carrinho; mostrar uma barrinha vermelha que começa verde, indicando a capacidade do carrinho<br>
            -opção de guardar todos os itens no storage, se estiver numa das 4 cidades--%>
            <dvl:exception test="${charIsOnline}" type="fail" i18nMessage="account.hasOnlineChars"/>
            <dvl:exception test="${warnCharIsOnline}" type="warning" i18nMessage="account.warn.hasOnlineChars"/>
        </dvl:body>
    </div>

    <dvl:list items="${inventory}" key="char.Inventory" isInventory="true" onclickHandlers="false"/>
    <dvl:list items="${cart}" key="char.Cart" onclickHandlers="false"/>
</dvl:page>