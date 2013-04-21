<%-- Created on : 28/07/2010, 01:32:53
     Author     : Wonka --%>
<%@tag description="Creates the news element." pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="message"%>

<dvl:body key="home.subtitle">
    <p>
        Apis é um Painel de Controle para o jogo Ragnarok Online.
    </p>
    <p>
        <fmt:message key="guest.info"/>
    </p>
    <p>
        Obs: Este site utiliza recursos HTML5 e CSS3, por isso é necessário a utilização de um navegador que tenha um bom suporte a esses padrões (Chrome, Safari e Firefox recomendados).
    </p>
    <article>
        <dvl:subtitle override="true" type="h4">Lorem Ipsum</dvl:subtitle>
        <section>
            <span class="tk-armalite-rifle">${randomName}</span>
            <span title="Title aleatório" class="tk-learning-curve">Elemento aleatório</span>
        </section>
    </article>
</dvl:body>