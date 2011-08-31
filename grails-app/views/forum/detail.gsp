<%@ page import="com.shaka.Forum" %>
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
            <h1>${fieldValue(bean: forumInstance, field: "nome")}</h1>
            <br />
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>
                                <g:message code="nomeTopico"/>
                            </th>
                            <th>
                                <g:message code="respostas"/>
                            </th>
                            <th>
                                <g:message code="visitas"/>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                      <g:if test="${Boolean.FALSE.equals(forumInstance.topicoList.isEmpty())}">
                              <g:each var="t" in="${forumInstance?.topicoList}" status="i">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                    <td>
                                        <g:link controller="debate" action="showTopico"  id="${t.id}">${t.titulo}</g:link>
                                    </td>
                                    <td>
                                        ${Mensagem.countByTopico(t)}
                                    </td>
                                    <td>
                                        ${fieldValue(bean: t, field: "numeroVisitas")}
                                    </td>
                                </tr>
                            </g:each>
                      </g:if>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                 <span class="button">
                     <g:link class="edit" controller="debate" action="index" id="${forumInstance.id}" ><g:message code="novoTopico" /></g:link>
                     <g:link class="edit" controller="forum" action="list" >${message(code: 'voltar')}</g:link>
                     <input type="button" class="edit" style="display:none">
                 </span>
            </div>
        </div>

    </body>
</html>
