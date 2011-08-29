<div class="nav">
    <span class="menuButton">
       <a class="home" href="${createLink(uri: '/')}">
           <g:message code="home"/>
       </a>
    </span>
</div>
 <div class="body">
     <h1><g:message code="novoTopico" /></h1>
     <g:if test="${flash.message}">
         <div class="message">${flash.message}</div>
     </g:if>
     <g:if test="${topicoInstance.hasErrors() || mensagemInstance.hasErrors()}">
         <div class="errors">
             <g:renderErrors bean="${topicoInstance}" as="list"  />
             <g:renderErrors bean="${mensagemInstance}" as="list" />
           </div>
     </g:if>
     <g:form action="saveTopico" >
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
                             <g:textField name="titulo" value="${topicoInstance?.titulo}" style="width:650px" maxlength="100" />
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
                 <g:submitButton name="salvar" class="save" value="${message(code: 'salvar')}" />
             </span>
         </div>
     </g:form>
</div>