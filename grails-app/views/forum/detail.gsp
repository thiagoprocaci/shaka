<%@ page import="com.shaka.Forum" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="shakaProject" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton">
                <a class="home" href="${createLink(uri: '/')}">
                    <g:message code="home"/>
                </a>
            </span>
        </div>
        <div class="body">
            <h1>${fieldValue(bean: forumInstance, field: "nome")}</h1>
            <div>
                <g:link controller="debate" action="index" id="${forumInstance.id}" ><g:message code="novoTopico" /></g:link>
            </div>
            <br />
            <div class="dialog">
                <ul>
                    <g:each var="t" in="${forumInstance?.topicoList}">
                        <li>
                          <g:link controller="debate" action="showTopico"  id="${t.id}">${t.titulo}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>
        </div>
    </body>
</html>
