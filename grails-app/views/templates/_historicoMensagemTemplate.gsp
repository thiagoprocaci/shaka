<%@ page import="com.shaka.Mensagem" %>
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
                         <div >
                            <g:if test="${m?.usuario?.pathImagem != null}">
                                <img src="${resource(dir: diretorioImagem, file: m?.usuario?.pathImagem)}" style="width:100px;height:100px" />
                            </g:if>
                            <g:if test="${m?.usuario?.pathImagem == null}">
                                <img src="${resource(dir: 'images/icones', file: 'anonimo.jpg')}" style="width:100px;height:100px" />
                            </g:if>
                        </div>
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
                     <td colspan="2">
                        ${m?.usuario?.assinatura?.decodeHTML()}
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
