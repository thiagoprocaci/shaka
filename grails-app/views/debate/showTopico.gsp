<%@ page import="com.shaka.Topico" %>
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
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <h1>${fieldValue(bean: topicoInstance, field: "titulo")}</h1>
            <div class="list">

                <table>
                   <tbody>
                        <g:each in="${topicoInstance?.mensagemList}" var="m" status="i">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                <td valign="top" class="value">
                                        ${m?.texto?.decodeHTML()}
                                </td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${topicoInstance?.id}" />
                    <span class="button">
                         <g:actionSubmit class="edit" action="responderTopico" value="${message(code: 'responder')}" />
                    </span>
                </g:form>
            </div>
        </div>
    </body>
</html>
