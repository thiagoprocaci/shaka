<%@ page import="com.shaka.Usuario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="shakaProject"  /></title>
    </head>
   <body>
        <g:menu/>
        <div class="body">
            <h1>
                 <g:message code="trocaSenha"/>
            </h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${usuarioInstance}">
            <div class="errors">
                <g:renderErrors bean="${usuarioInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="updatePassword" method="POST">
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="senha"><g:message code="senha"  /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="senha" value="" maxlength="255" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="confirmacaoSenha"><g:message code="confirmacaoSenha"  /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="confirmacaoSenha" value="" maxlength="255" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <g:hiddenField name="version" value="${usuarioInstance?.version}" />
                <div class="buttons">
                    <span class="button">
                        <g:submitButton name="save" class="save" value="${message(code: 'salvar')}" />
                    </span>
                </div>
            </g:form>
        </div>
    </body>
</html>
