<%@ page import="com.shaka.Usuario" %>
<g:menu/>
<div class="body">
    <h1>
        <g:if test="${usuarioInstance.id == null}">
            <g:message code="novoUsuario"/>
        </g:if>
        <g:if test="${usuarioInstance.id != null}">
            <g:message code="editarUsuario"/>
        </g:if>
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
    <g:form action="${action}" enctype="multipart/form-data">
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

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="nome"><g:message code="nome"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'nome', 'errors')}">
                            <g:textField name="nome" value="${usuarioInstance?.nome}" />
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="username"><g:message code="login"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'username', 'errors')}">
                            <g:textField name="username" value="${usuarioInstance?.username}" />
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="email"><g:message code="email"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'email', 'errors')}">
                            <g:textField name="email" value="${usuarioInstance?.email}" />
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="password"><g:message code="senha"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'password', 'errors')}">
                            <g:passwordField name="password" value="${usuarioInstance?.password}" />
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="assinatura"><g:message code="assinatura"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'assinatura', 'errors')}">
                            <g:textArea name="assinatura" value="${usuarioInstance?.assinatura}" />
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
