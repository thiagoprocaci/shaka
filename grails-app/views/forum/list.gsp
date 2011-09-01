<%@ page import="com.shaka.Forum" %>
<%@ page import="com.shaka.Topico" %>
<%@ page import="com.shaka.Mensagem" %>
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
            <div class="list">
                 <table>
                        <thead>
                            <tr>
                                <th>
                                    <g:message code="nomeForum"/>
                                </th>
                                <th>
                                    <g:message code="topicos"/>
                                </th>
                                <th>
                                    <g:message code="mensagens"/>
                                </th>
                                <th>
                                    <g:message code="ultimaMensagem"/>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                           <g:each var="f" in="${Forum.list()}" status="i">
                             <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                 <td>
                                     <g:link controller="forum" action="detail" id="${f.id}">${f.nome}</g:link>
                                     <br /> ${f.descricao}
                                 </td>
                                 <td>
                                     ${Topico.countByForum(f)}
                                 </td>
                                 <td>
                                    ${Mensagem.countByForum(f)}
                                 </td>
                                 <td>
                                     <g:formatDate format="dd/MM/yyyy HH:mm:ss" date="${Mensagem.getLast(f)?.dateCreated}"/>
                                 </td>
                             </tr>
                           </g:each>
                        </tbody>
                    </table>
            </div>
        </div>
    </body>
</html>
