<%@ page import="com.shaka.Topico" %>
<%@ page import="com.shaka.Mensagem" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'topico.label', default: 'Topico')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton">
               <a class="home" href="${createLink(uri: '/')}">
                       <g:message code="default.home.label"/>
               </a>
            </span>
            <span class="menuButton">
                <g:link class="list" action="list">
                    <g:message code="default.list.label" args="[entityName]" />
                </g:link>
            </span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${topicoInstance}">
            <div class="errors">
                <g:renderErrors bean="${topicoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:hasErrors bean="${mensagemInstance}">
            <div class="errors">
                <g:renderErrors bean="${mensagemInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="saveResposta" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="titulo"><g:message code="topico.titulo.label" default="Titulo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: topicoInstance, field: 'titulo', 'errors')}">
                                    ${fieldValue(bean: topicoInstance, field: "titulo")}
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="titulo"><g:message code="topico.titulo.label" default="Texto" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensagemInstance, field: 'texto', 'errors')}">
                                    <g:textArea name="texto" value="${mensagemInstance?.texto}" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button">
                       <g:submitButton name="create" class="save" value="${message(code: 'enviar', default: 'Enviar')}" />
                    </span>
                </div>
                <g:hiddenField name="id" value="${topicoInstance?.id}" />
            </g:form>
        </div>
    </body>
</html>
