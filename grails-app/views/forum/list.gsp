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
            <h1><g:message code="foruns" /></h1>
            <div class="dialog">
                <ul>
                    <g:each var="f" in="${Forum.list()}">
                        <li>
                          <g:link controller="forum" action="detail" id="${f.id}">${f.nome}</g:link> <br />
                          ${f.descricao} <br /><br />
                        </li>
                    </g:each>
                </ul>
            </div>
        </div>
    </body>
</html>
