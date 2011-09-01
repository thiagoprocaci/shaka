
<%@ page import="com.shaka.Usuario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "username")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.password.label" default="Password" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "password")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "email")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.nome.label" default="Nome" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "nome")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.assinatura.label" default="Assinatura" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "assinatura")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.pathImagem.label" default="Path Imagem" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "pathImagem")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.accountExpired.label" default="Account Expired" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.accountExpired}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.accountLocked.label" default="Account Locked" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.accountLocked}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${usuarioInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.enabled.label" default="Enabled" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.enabled}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usuario.passwordExpired.label" default="Password Expired" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.passwordExpired}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${usuarioInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
