<%--Created on : 25/09/2010, 23:13:26
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Access Logs</title>
        <style type="text/css">
            @import url("http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css");
            body{
                background-color: white;                
            }
            section{
                margin: auto;
                width: 700px;
            }
            div{
                margin: 1em;
                padding: 1em;                
                color: white;
            }
            blockquote, em{
                text-shadow: black 1px 1px;
            }            
            h2, blockquote{
                background-color: hsl(220, 60%, 70%);
            }
            h2{
                font-size: large;
            }
            .access, em{
                font-weight: bold;
                color: white;
            }
            .annonymous.access{
                background-color: hsl(120, 30%, 50%);
            }
            .annonymous.access h2, .annonymous.access blockquote{
                background-color: hsl(120, 30%, 60%);
            }
            .local.access{
                background-color: silver;
            }
            .registered.access{
                background-color: hsl(220, 60%, 60%);
            }
            div, h2, h3, h4, blockquote{
                border-radius: 6px;
                margin: .5em;
                padding: .5em;                
            }
        </style>
        <script src="http://www.dvlcube.com/Apis/usr/script/RequestObject.js"> </script>
        <script>
            function getMore(){
                
            }
        </script>
    </head>
    <body>
    <section>
        <c:if test="${accessLog != null}">
            <c:forEach items="${accessLog}" var="log">
                <div class="${pageContext.request.localAddr == log.host? "local access" : (not empty log.member.name? "registered access" : "annonymous access")}">
                    <h2>
                        <em>${log.member.name}</em> (${log.locale}${log.host}) at <em>${log.time}</em>
                    </h2>
                    <h3>
                        <em>Action</em> ${log.resourceName}
                    </h3>
                    <c:if test="${not empty log.resourceURL}">
                        <h3>
                            <em>URL</em> ${log.resourceURL}
                        </h3>
                    </c:if>
                    <h3>
                        <em>System</em>
                        ${log.system.vendor} ${log.system.name} <em>${log.system.version}</em>
                        with ${log.browser.vendor} ${log.browser.name} <em>${log.browser.version}</em>
                    </h3>
                    <blockquote>
                        ${log.agent}
                    </blockquote>
                </div>
            </c:forEach>
        </c:if>
        <input type="button" value="more" onclick="getMore()">
    </section>
    </body>
</html>