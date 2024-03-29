<%@page import="com.shaka.AvaliacaoMensagem"%>
<%@ page import="com.shaka.Mensagem" %>
<%@ page import="com.shaka.AvaliacaoMensagem" %>
<%@ page import="com.shaka.DebateController" %>
<div class="historicoMensagemList">
     <table>
         <thead>
             <tr>
                <th>
                    <g:message code="autor"/>
                </th>
                <th>
                    <g:message code="mensagem"/>
                </th>
            </tr>
         </thead>
        <tbody>
             <g:each in="${mensagemList}" var="m" status="i">
                  <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                     <td colspan="2">
                         <g:message code="postadaEm"  />
                         <g:formatDate format="dd/MM/yyyy HH:mm:ss" date="${m?.dateCreated}"/>
                     </td>
                  </tr>
                 <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                     <td class="usuarioTd">
                         <div>
                            ${m?.usuario?.nome}
                         </div>
                         <g:imagemUsuario usuarioInstance="${m?.usuario}"/>
                        <div>
                             <g:message code="membroDesde" />
                             <g:formatDate format="dd/MM/yyyy HH:mm:ss" date="${m?.usuario?.dateCreated}"/>
                         </div>
                         <div>
                             <g:message code="mensagens_ponto" />
                             ${Mensagem.countByUsuario(m?.usuario)}
                         </div>
                     </td>
                     <td valign="top" class="value">
                             ${m?.texto?.decodeHTML()}
                     </td>
                 </tr>
                 <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                     <td colspan="2" id="message_${m.id}">
                        ${m?.usuario?.assinatura?.decodeHTML()}
                        <sec:ifLoggedIn>
                            <div>
                               ${DebateController.getTextoAvaliacaoMensagem(m)}
                            </div>
                           <g:if test="${!AvaliacaoMensagem.avaliouMensagem(m,sec.loggedInUserInfo(field:'username'))}">
                           		<g:remoteLink action="avaliarMensagem" id="${m.id}" params="${[avaliacao:true]}" update="message_${m.id}">
                               		<g:message code="gostei"/>
                           		</g:remoteLink>
                            	<g:remoteLink action="avaliarMensagem" id="${m.id}" params="${[avaliacao:false]}" update="message_${m.id}">
                                	<g:message code="naoGostei"/>
                            	</g:remoteLink>
                           </g:if>
                        </sec:ifLoggedIn>
                     </td>
                 </tr>
             </g:each>
         </tbody>
     </table>
 </div>
 <g:if test="${mensagemTotal != null && topicoInstance != null}">
    <div class="paginateButtons">
        <g:paginate total="${mensagemTotal}" id="${topicoInstance.id}" />
    </div>
 </g:if>
