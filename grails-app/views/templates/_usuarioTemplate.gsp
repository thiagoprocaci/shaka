<%@ page import="com.shaka.Usuario" %>
<g:menu/>
<div class="body">
    <h1>
        <g:if test="${usuarioInstance.id == null}">
            <g:message code="novoUsuario"/>
        </g:if>
        <g:if test="${usuarioInstance.id != null}">
            <g:message code="meuPerfil"/>
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

    <g:form action="${action}" enctype="multipart/form-data" method="POST">
        <div class="dialog">
            <table>
                <tbody>

					<tr class="prop">
                        <td valign="top" class="name">
                            <label for="username"><g:message code="login"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'username', 'errors')}">
                            <g:if test="${usuarioInstance.id == null}">
                                <g:textField name="username" value="${usuarioInstance?.username}" maxlength="255" />
                            </g:if>
                            <g:if test="${usuarioInstance.id != null}">
                                ${usuarioInstance?.username}
                            </g:if>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="nome"><g:message code="nome"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'nome', 'errors')}">
                            <g:textField name="nome" value="${usuarioInstance?.nome}" maxlength="255" />
                        </td>
                    </tr>



                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="email"><g:message code="email"  /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'email', 'errors')}">
                            <g:textField name="email" value="${usuarioInstance?.email}" maxlength="255" />
                        </td>
                    </tr>

                    <g:if test="${usuarioInstance.id == null}">
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
                    </g:if>

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
