<%--Created on : 16/10/2009, 17:02:00
    Author     : Wonka--%>
<%@tag description="Standard page header and footer. Body goes inside." pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>

<%@attribute name="preTitle" description="A String to be placed before the title key."
             required="false" rtexprvalue="true"%>
<%@attribute name="titleKey" description="The i18n key representing this page's title."
             required="true" rtexprvalue="true"%>
<%@attribute name="titleAid" description="Additional information to be displayed in this page's title."
             required="false" rtexprvalue="true"%>
<%@attribute name="script" description="The location of a script that is required for the proper function of this page. This script will be loaded before the page is displayed."
             required="false" rtexprvalue="true"%>
<%@attribute name="scripts" description="A list of required scripts, separated by commas."
             required="false" rtexprvalue="true"%>
<%@attribute name="aidScript" description="The location of an additional script that will provide functionality to this page. This script will be loaded *after* the page is displayed, and will be placed in the bottom of the page, to improve performance."
             required="false" rtexprvalue="false"%>
<%@attribute name="ajax" description="Whether this page requires the ajax request object."
             required="false" type="java.lang.Boolean" rtexprvalue="true"%>
<%@attribute name="style" description="An additional style sheet that will be used to display the page." %>
<%@attribute name="styles" description="An additional list of style sheets that will be used to display the page. The files are separated by commas" %>
<%@attribute name="editable" description="Whether the style of this page can be edited in real-time using the brosho jquery plugin."
             required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@attribute name="description" description="Page description"
             required="false" rtexprvalue="true"%>
<%@attribute name="keywords" description="Appends kyewords to the header meta tag"
             required="false" rtexprvalue="true"%>
<%@attribute name="autorefresh" description=""
             required="false" rtexprvalue="true"%>
<%String[] scriptList = null;
            String[] styleList = null;
            if (scripts != null) {
                scriptList = scripts.split(",");
            }
            if (styles != null) {
                styleList = styles.split(",");
            }%>
<!DOCTYPE html>
<html manifest="cache.manifest">
    <head><c:if test="${not empty autorefresh}"><meta http-equiv="refresh" content="${autorefresh}"/></c:if>
        <link rel="shortcut icon" href="./usr/FAVICON.ico" type="image/x-icon">
        <meta charset="UTF-8"/>
        <meta name="google-site-verification" content="OlggTd5IafOWWgFJKAPEwFMYR0uNf5lOJHxtyirRitU"/>
        <meta name="author" content="Ulisses Lima"/>
        <!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
        <c:choose>
            <c:when test="${not empty description}">
                <meta name="description" content="${description}"/>
            </c:when>
            <c:otherwise>
                <meta name="description" content="Painel de controle Apis."/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${not empty keywords}">
                <meta name="keywords" content="Apis, ragnarok, ${keywords}"/>
            </c:when>
            <c:otherwise>
                <meta name="keywords" content="Apis, ragnarok, social network, games, forums, file sharing, virtual storage, dvlcube"/>
            </c:otherwise>
        </c:choose>

        <style>
            @import url("http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css");
            @import url("./usr/style/body.css");
            @import url("./usr/style/theme/default.css");
            <c:if test="${not empty style}">@import url("./usr/style/${style}");</c:if>
            <% if (styleList != null) {
                            for (String styleSrc : styleList) {%>
            @import url("./usr/style/<% out.print(styleSrc);%>");
            <%  }
                        }%>
        </style>

        <%--<script type="text/javascript" src="http://use.typekit.com/mas4rdt.js"></script>
        <script type="text/javascript">try{Typekit.load();}catch(e){}</script>--%>
        <script src="./usr/script/Main.js"> </script>
        <c:if test="${!userSession.valid}"><script src="./usr/script/LoginWindow.js"> </script></c:if>
        <c:if test="${ajax}">
            <script src="./usr/script/RequestObject.js"> </script>
            <script src="./usr/script/StateChanges.js"> </script>
            <script src="./usr/script/Terminal.js"> </script>
        </c:if>
        <c:if test="${not empty script}"><script src="./usr/script/${script}"> </script></c:if>
        <title>
            ${initParam["ServerName"]}@Apis - <fmt:message key="${titleKey}"/>
            ${not empty titleAid? titleAid : ""}
        </title>
        <c:if test="${editable}"><script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script><script src="./usr/lib/brosho.js"></script></c:if>
    </head>
    <body>
    <header>
        <h1>
            ${not empty preTitle? preTitle : ""}
            <fmt:message key="${titleKey}"/>
            ${not empty titleAid? titleAid : ""}
        </h1>
    </header>
    <nav>
        <ul>            
            <c:choose>
                <c:when test="${userSession.valid}">                    
                    <li class="menu-item">
                        <a href="./Home">
                            <img src="http://dl.dropbox.com/u/7798003/icons/go-home.png" alt="Profile"/>
                        </a>
                        <span><fmt:message key="profile.Title"/></span>
                    </li>
                    <li class="menu-item">
                        <a href="./Mail">
                            <img src="http://dl.dropbox.com/u/7798003/icons/indicator-messages.svg" alt="mail"/>
                        </a>
                        <span><fmt:message key="account.mail"/></span>
                    </li>
                    <li class="menu-item">
                        <a href="./Accounts">
                            <img src="http://dl.dropbox.com/u/7798003/icons/emblem-documents.png" alt="Accounts"/>
                        </a>
                        <span><fmt:message key="menu.myAccounts"/></span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="menu-item">
                        <a href="./Index">
                            <img src="http://dl.dropbox.com/u/7798003/icons/go-home.png" alt="Index"/>
                        </a>
                        <span>Home</span>
                    </li>
                    <li class="menu-item">
                        <a href="javascript:show_loginWindow()">
                            <img src="http://dl.dropbox.com/u/7798003/icons/login.png" alt="Login"/>
                        </a>
                        <span>Login</span>
                    </li>
                    <li class="menu-item">
                        <a href="./Signup">
                            <img src="http://dl.dropbox.com/u/7798003/icons/signup.png" alt="Signup"/>
                        </a>
                        <span><fmt:message key="signup.title"/></span>
                    </li>
                </c:otherwise>                
            </c:choose>
            <li class="menu-item">
                <a href="./OnlinePlayers">
                    <img src="http://dl.dropbox.com/u/7798003/icons/emblem-important.png" alt="Online Players"/>
                </a>
                <span><fmt:message key="menu.onlinePlayers"/></span>
            </li>
        </ul>
    </nav>    
    <jsp:doBody />
    <section id="console" class="trans shadow">
        <section id="console-log"><h5><b>Type /help for help</b></h5></section>
        <input type="text" tabindex="1" size="25" onkeypress="sendCommand(this)"
               onfocus="prompt('show')" onblur="prompt('hide')">
    </section>
    <footer>
        <span style="float: left">::..</span>
        <c:choose>
            <c:when test="${userSession.valid}">                
                <a href="./Profile?id=${userSession.nameURL}">${userSession.name}@${initParam["ServerName"]}</a>
            </c:when>
            <c:otherwise>
                Apis © 2010
            </c:otherwise>
        </c:choose>
        • <a href="#not_available_yet">About</a> <a href="http://www.dvlcube.com/Cube/tos">Terms</a> <a href="#not_available_yet">Help</a>
        <c:if test="${userSession.valid}"><a href="./Logout"><fmt:message key="menu.logout"/></a></c:if>
        <span style="float: right">..::</span>
    </footer>
</body>
<script src="./usr/script/Window.js"> </script>
<c:if test="${not empty aidScript}">
    <script src="./usr/script/${aidScript}"> </script>
</c:if>
<%if (scriptList != null) {
                for (String scriptSrc : scriptList) {%>
<script defer src="./usr/script/<% out.print(scriptSrc);%>"> </script>
<%  }
            }%>
<c:if test="${sessionScope['facebook.user.client'] != null}">
    <div id="FB_HiddenIFrameContainer" style="display:none; position:absolute; left:-100px; top:-100px; width:0px; height: 0px;"></div><script src="http://static.ak.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php" type="text/javascript"></script><script type="text/javascript">  FB_RequireFeatures(["CanvasUtil"], function(){    FB.XdComm.Server.init("/facebook/xd_receiver.htm");    FB.CanvasClient.startTimerToSizeToContent();  });</script>
</c:if>
</html>