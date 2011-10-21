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
        <g:message code="imagemUsuario"/>
    </h1>
    <g:if test="${message}">
        <div class="message">${message}</div>
    </g:if>
    <g:hasErrors bean="${usuarioInstance}">
    <div class="errors">
        <g:renderErrors bean="${usuarioInstance}" as="list" />
    </div>
    </g:hasErrors>
    <g:if test="${usuarioInstance?.pathImagem != null}">
        <div >
            <img src="${resource(dir: diretorioImagem, file: usuarioInstance?.pathImagem)}" style="width:140px;height:140px" />
        </div>
    </g:if>
    <g:form action="update" enctype="multipart/form-data" method="POST">
        <div class="dialog">
            <table>
                <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <g:message code="imagem"  />
                            </td>
                            <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'pathImagem', 'errors')}">
                                <input type="file" name="file" id="file"/>
                            </td>
                        </tr>
                </tbody>
            </table>
        </div>
        <g:hiddenField name="version" value="${usuarioInstance?.version}" />
        <div class="buttons">
            <span class="button">
                <g:submitButton  name="save" class="save" value="${message(code: 'salvar')}" />
            </span>
        </div>
    </g:form>
</div>
</body>
</html>
