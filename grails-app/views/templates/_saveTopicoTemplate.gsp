<%@ page import="org.springframework.util.StringUtils"%>
<%@ page import="com.shaka.Topico" %>
<%@ page import="com.shaka.Mensagem" %>
<g:menu/>
 <div class="body">
     <g:if test="${message}">
         <div class="message">${message}</div>
     </g:if>
     <g:if test="${topicoInstance.hasErrors() || mensagemInstance.hasErrors()}">
         <div class="errors">
             <g:renderErrors bean="${topicoInstance}" as="list"  />
             <g:renderErrors bean="${mensagemInstance}" as="list" />
           </div>
     </g:if>
     <g:if test="${visualizar}">
             <h1><g:message code="visualizacao" /></h1>
             <h2>${fieldValue(bean: topicoInstance, field: "titulo")}</h2>
             <div class="list">
                <table>
                   <tbody>
                       <tr class="odd">
                           <td valign="top" class="value">
                                   ${mensagemInstance?.texto?.decodeHTML()}
                           </td>
                       </tr>
                    </tbody>
                </table>
            </div>
     <hr></hr>
     </g:if>
     <g:if test="${topicoInstance.id == null}">
         <h1><g:message code="novoTopico" /></h1>
     </g:if>
     <g:if test="${topicoInstance.id != null}">
         <h1><g:message code="responderA" args="${[topicoInstance.titulo]}" /></h1>
     </g:if>
     <g:form>
         <div class="dialog">
             <table>
                 <tbody>
                     <tr class="prop">
                         <td valign="top" class="name">
                             <label for="titulo">
                                 <g:message code="titulo" />
                             </label>
                         </td>
                         <td valign="top" class="value ${hasErrors(bean: topicoInstance, field: 'titulo', 'errors')}">
                             <g:if test="${topicoInstance.id == null}">
                                 <g:textField name="titulo" value="${topicoInstance?.titulo}" style="width:650px" maxlength="100" />
                             </g:if>
                             <g:if test="${topicoInstance.id != null}">
                                 ${fieldValue(bean: topicoInstance, field: "titulo")}
                                 <g:hiddenField name="id" value="${topicoInstance.id}"/>
                             </g:if>
                             <g:if test="${forumId != null}">
                                 <g:hiddenField name="forumId" value="${forumId}"/>
                             </g:if>
                         </td>
                     </tr>
                     <tr class="prop">
                         <td valign="top" class="name">
                             <label for="texto">
                                 <g:message code="texto"  />
                             </label>
                         </td>
                         <td valign="top" class="value ${hasErrors(bean: mensagemInstance, field: 'texto', 'errors')}">
                             <fckeditor:editor name="texto" height="500">
                               ${fieldValue(bean:mensagemInstance,field:'texto').decodeHTML()}
                             </fckeditor:editor>
                         </td>
                     </tr>
                 </tbody>
             </table>
         </div>
         <div class="buttons">
             <span class="button">
                 <g:actionSubmit class="save" action="${saveAction}" value="${message(code: 'salvar')}" />
                 <g:actionSubmit class="edit" action="${visualizarAction}" value="${message(code: 'visualizar')}" />
                 <g:if test="${topicoInstance.id == null}">
                     <g:link class="edit" controller="forum" action="detail" id="${forumId}">${message(code: 'voltar')}</g:link>
                 </g:if>
                 <g:if test="${topicoInstance.id != null}">
                      <g:link class="edit" controller="debate" action="showTopico" id="${topicoInstance.id}">${message(code: 'voltar')}</g:link>
                 </g:if>
             </span>
         </div>
         <g:if test="${topicoInstance.id != null  && mensagemList != null}">
             <hr></hr>
             <h2><g:message code="historicoUltimasMensagens"/></h2>
             <g:historicoMensagens mensagemList="${mensagemList}" diretorioImagem="${diretorioImagem}" />
         </g:if>
     </g:form>
</div>