<%@ page import="com.shaka.Topico" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="shakaProject" /></title>
    </head>
    <body>
        <g:menu/>
        <div class="body">
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <h1>${fieldValue(bean: topicoInstance, field: "titulo")}</h1>
            <g:historicoMensagens mensagemList="${topicoInstance?.mensagemList}" diretorioImagem="${diretorioImagem}" />
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${topicoInstance?.id}" />
                    <span class="button">
                         <sec:ifLoggedIn>
                             <g:actionSubmit class="edit" action="responderTopico" value="${message(code: 'responder')}" />
                         </sec:ifLoggedIn>
                         <g:link class="edit" controller="forum" action="detail" id="${topicoInstance.forum.id}">${message(code: 'voltar')}</g:link>
                    </span>
                </g:form>
            </div>
        </div>
    </body>
</html>
