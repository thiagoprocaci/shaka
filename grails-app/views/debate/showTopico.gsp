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
            <h1><g:message code="default.show.label"  /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                ${fieldValue(bean: topicoInstance, field: "titulo")}
                <table>
                   <tbody>
                        <g:each in="${topicoInstance?.mensagemList}" var="m">
                            <tr class="prop">
                                <td valign="top" class="value">
                                    <p>
                                        ${m?.texto?.decodeHTML()}
                                    </p>
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
